/**
 * Voice Coding Agent — Frontend
 * Uses Web Speech API for STT and the server's /api/chat SSE endpoint.
 */

// ─── State ────────────────────────────────────────────────────────
const state = {
  messages: [],        // [{role:'user'|'assistant', content:string}]
  recording: false,
  streaming: false,
  ttsEnabled: true,
  abortController: null,
  recognition: null,
  tts: window.speechSynthesis,
};

// ─── DOM refs ─────────────────────────────────────────────────────
const micBtn       = document.getElementById('mic-btn');
const messagesEl   = document.getElementById('messages');
const transcriptBar = document.getElementById('transcript-bar');
const transcriptText = document.getElementById('transcript-text');
const clearBtn     = document.getElementById('clear-btn');
const abortBtn     = document.getElementById('abort-btn');
const ttsToggle    = document.getElementById('tts-toggle');
const ttsIcon      = document.getElementById('tts-icon');
const tokenInfo    = document.getElementById('token-info');
const toast        = document.getElementById('toast');

// ─── Speech Recognition setup ────────────────────────────────────
function createRecognition() {
  const SR = window.SpeechRecognition || window.webkitSpeechRecognition;
  if (!SR) return null;

  const r = new SR();
  r.continuous = false;
  r.interimResults = true;
  r.maxAlternatives = 1;
  r.lang = navigator.language || 'en-US';
  return r;
}

// ─── Toast ────────────────────────────────────────────────────────
let toastTimer = null;
function showToast(msg, duration = 4000) {
  toast.textContent = msg;
  toast.classList.remove('hidden');
  clearTimeout(toastTimer);
  toastTimer = setTimeout(() => toast.classList.add('hidden'), duration);
}

// ─── Simple markdown renderer ─────────────────────────────────────
// Handles code blocks, inline code, bold, italic, headers, lists.
function renderMarkdown(text) {
  // Escape HTML first, then selectively render markdown
  const escape = (s) =>
    s.replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');

  // Split into code-block and non-code-block segments
  const parts = text.split(/(```[\s\S]*?```)/g);
  return parts.map((part, i) => {
    if (i % 2 === 1) {
      // Code block
      const inner = part.slice(3).replace(/^.*\n?/, '').replace(/```$/, '').trimEnd();
      return `<pre><code>${escape(inner)}</code></pre>`;
    }
    // Normal text — apply inline rules
    let s = escape(part);
    s = s.replace(/`([^`]+)`/g, '<code>$1</code>');
    s = s.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
    s = s.replace(/\*(.+?)\*/g, '<em>$1</em>');
    // Headers
    s = s.replace(/^### (.+)$/gm, '<h3>$1</h3>');
    s = s.replace(/^## (.+)$/gm, '<h2>$1</h2>');
    s = s.replace(/^# (.+)$/gm, '<h1>$1</h1>');
    // Unordered lists
    s = s.replace(/^\s*[-*] (.+)$/gm, '<li>$1</li>');
    s = s.replace(/(<li>.*<\/li>(\n|$))+/g, (m) => `<ul>${m}</ul>`);
    // Paragraphs (blank line separation)
    s = s.replace(/\n{2,}/g, '</p><p>');
    // Line breaks
    s = s.replace(/\n/g, '<br>');
    return `<p>${s}</p>`;
  }).join('');
}

// ─── Message rendering ────────────────────────────────────────────
function appendMessage(role, content, streaming = false) {
  const div = document.createElement('div');
  div.className = `message ${role}`;

  const bubble = document.createElement('div');
  bubble.className = 'bubble';

  if (streaming) {
    bubble.innerHTML = '<span class="cursor"></span>';
  } else {
    bubble.innerHTML = renderMarkdown(content);
  }

  div.appendChild(bubble);
  messagesEl.appendChild(div);
  scrollToBottom();
  return bubble;
}

function scrollToBottom() {
  const main = document.getElementById('chat-container');
  main.scrollTop = main.scrollHeight;
}

// ─── Text-to-Speech ───────────────────────────────────────────────
function speak(text) {
  if (!state.ttsEnabled || !state.tts) return;
  state.tts.cancel();
  // Strip markdown for TTS readability
  const plain = text
    .replace(/```[\s\S]*?```/g, 'code block')
    .replace(/`[^`]+`/g, '')
    .replace(/[#*_~]/g, '')
    .replace(/\n+/g, ' ')
    .trim();
  if (!plain) return;

  const utt = new SpeechSynthesisUtterance(plain);
  utt.rate = 1.05;
  utt.pitch = 1;
  state.tts.speak(utt);
}

// ─── Chat with server ─────────────────────────────────────────────
async function sendPrompt(prompt) {
  if (state.streaming) return;

  // Add user message to UI and history
  appendMessage('user', prompt);
  state.messages.push({ role: 'user', content: prompt });

  // Show assistant streaming bubble
  const bubble = appendMessage('assistant', '', true);
  let fullText = '';

  state.streaming = true;
  abortBtn.classList.remove('hidden');
  micBtn.disabled = true;

  state.abortController = new AbortController();

  try {
    const res = await fetch('/api/chat', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        messages: state.messages.slice(0, -1), // history before current prompt
        prompt,
      }),
      signal: state.abortController.signal,
    });

    if (!res.ok) {
      const err = await res.json().catch(() => ({ error: `HTTP ${res.status}` }));
      throw new Error(err.error || `HTTP ${res.status}`);
    }

    const reader = res.body.getReader();
    const decoder = new TextDecoder();
    let buffer = '';

    while (true) {
      const { done, value } = await reader.read();
      if (done) break;

      buffer += decoder.decode(value, { stream: true });

      // Parse SSE events
      const eventBlocks = buffer.split('\n\n');
      buffer = eventBlocks.pop(); // keep incomplete block

      for (const block of eventBlocks) {
        const lines = block.split('\n');
        let eventType = 'message';
        let dataLine = '';

        for (const line of lines) {
          if (line.startsWith('event: ')) eventType = line.slice(7).trim();
          if (line.startsWith('data: ')) dataLine = line.slice(6).trim();
        }

        if (!dataLine) continue;
        let payload;
        try { payload = JSON.parse(dataLine); } catch { continue; }

        if (eventType === 'delta' && payload.text) {
          fullText += payload.text;
          bubble.innerHTML = renderMarkdown(fullText) + '<span class="cursor"></span>';
          scrollToBottom();
        } else if (eventType === 'done') {
          if (payload.inputTokens != null) {
            tokenInfo.innerHTML = `&#8593;${payload.inputTokens}&nbsp; &#8595;${payload.outputTokens}`;
          }
        } else if (eventType === 'error') {
          throw new Error(payload.message || 'Stream error');
        }
      }
    }

    // Finalize
    bubble.innerHTML = renderMarkdown(fullText);
    state.messages.push({ role: 'assistant', content: fullText });
    if (fullText) speak(fullText);

  } catch (err) {
    if (err.name === 'AbortError') {
      bubble.innerHTML = renderMarkdown(fullText || '_Response stopped._');
      if (fullText) {
        state.messages.push({ role: 'assistant', content: fullText });
      }
    } else {
      bubble.innerHTML = `<span style="color:#f87171">Error: ${err.message}</span>`;
      showToast(`Error: ${err.message}`);
      console.error(err);
    }
  } finally {
    state.streaming = false;
    state.abortController = null;
    abortBtn.classList.add('hidden');
    micBtn.disabled = false;
    scrollToBottom();
  }
}

// ─── Recording flow ───────────────────────────────────────────────
function startRecording() {
  if (state.recording || state.streaming) return;

  const SR = window.SpeechRecognition || window.webkitSpeechRecognition;
  if (!SR) {
    showToast('Web Speech API is not supported in this browser. Try Chrome or Safari.');
    return;
  }

  // Cancel TTS while user speaks
  if (state.tts) state.tts.cancel();

  const recognition = createRecognition();
  state.recognition = recognition;
  state.recording = true;

  micBtn.classList.add('recording');
  micBtn.setAttribute('aria-pressed', 'true');
  transcriptBar.classList.remove('hidden');
  transcriptText.textContent = 'Listening…';

  let finalTranscript = '';
  let interimTranscript = '';

  recognition.onresult = (e) => {
    interimTranscript = '';
    finalTranscript = '';
    for (let i = e.resultIndex; i < e.results.length; i++) {
      const t = e.results[i][0].transcript;
      if (e.results[i].isFinal) finalTranscript += t;
      else interimTranscript += t;
    }
    transcriptText.textContent = (finalTranscript || interimTranscript) || 'Listening…';
  };

  recognition.onerror = (e) => {
    if (e.error === 'no-speech') return; // ignore silence
    if (e.error === 'aborted') return;
    showToast(`Mic error: ${e.error}`);
    console.warn('Speech recognition error:', e.error);
  };

  recognition.onend = () => {
    stopRecording(finalTranscript.trim());
  };

  try {
    recognition.start();
  } catch (e) {
    showToast('Could not start microphone: ' + e.message);
    stopRecording('');
  }
}

function stopRecording(transcript) {
  if (!state.recording) return;
  state.recording = false;

  if (state.recognition) {
    try { state.recognition.stop(); } catch (_) {}
    state.recognition = null;
  }

  micBtn.classList.remove('recording');
  micBtn.setAttribute('aria-pressed', 'false');
  transcriptBar.classList.add('hidden');
  transcriptText.textContent = 'Listening…';

  if (transcript) {
    sendPrompt(transcript);
  }
}

// ─── Event: Hold-to-talk (touch + mouse) ─────────────────────────
// Touch
micBtn.addEventListener('touchstart', (e) => {
  e.preventDefault();
  startRecording();
}, { passive: false });

micBtn.addEventListener('touchend', (e) => {
  e.preventDefault();
  if (state.recognition) {
    try { state.recognition.stop(); } catch (_) {}
  }
}, { passive: false });

micBtn.addEventListener('touchcancel', (e) => {
  e.preventDefault();
  if (state.recognition) {
    try { state.recognition.stop(); } catch (_) {}
  }
}, { passive: false });

// Mouse (for desktop testing)
micBtn.addEventListener('mousedown', (e) => {
  e.preventDefault();
  startRecording();
});

window.addEventListener('mouseup', () => {
  if (state.recording && state.recognition) {
    try { state.recognition.stop(); } catch (_) {}
  }
});

// Space bar shortcut
window.addEventListener('keydown', (e) => {
  if (e.code === 'Space' && e.target === document.body && !state.recording && !state.streaming) {
    e.preventDefault();
    startRecording();
  }
});
window.addEventListener('keyup', (e) => {
  if (e.code === 'Space' && state.recording && state.recognition) {
    e.preventDefault();
    try { state.recognition.stop(); } catch (_) {}
  }
});

// ─── Abort streaming ──────────────────────────────────────────────
abortBtn.addEventListener('click', () => {
  if (state.abortController) state.abortController.abort();
});

// ─── Clear conversation ───────────────────────────────────────────
clearBtn.addEventListener('click', () => {
  if (state.streaming) {
    if (state.abortController) state.abortController.abort();
  }
  state.messages = [];
  tokenInfo.textContent = '';
  messagesEl.innerHTML = `
    <div class="message assistant">
      <div class="bubble">
        <p>Conversation cleared. Hold the mic button to speak.</p>
      </div>
    </div>`;
});

// ─── TTS toggle ───────────────────────────────────────────────────
ttsToggle.addEventListener('click', () => {
  state.ttsEnabled = !state.ttsEnabled;
  ttsIcon.textContent = state.ttsEnabled ? '\u{1F50A}' : '\u{1F507}';
  if (!state.ttsEnabled && state.tts) state.tts.cancel();
});

// ─── Init ─────────────────────────────────────────────────────────
(async function init() {
  // Check server health
  try {
    const r = await fetch('/api/health');
    if (!r.ok) throw new Error(`HTTP ${r.status}`);
  } catch (e) {
    showToast('Cannot reach server. Make sure it is running.', 8000);
  }

  // Warn if speech recognition unavailable
  if (!window.SpeechRecognition && !window.webkitSpeechRecognition) {
    showToast('Speech recognition not available. Try Chrome on Android or Safari on iOS.', 8000);
    micBtn.disabled = true;
  }

  // Request mic permission proactively (best-effort)
  if (navigator.mediaDevices?.getUserMedia) {
    navigator.mediaDevices.getUserMedia({ audio: true })
      .then((stream) => stream.getTracks().forEach((t) => t.stop()))
      .catch(() => {}); // permission denied is fine, handled at record time
  }
})();
