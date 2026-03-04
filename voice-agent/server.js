import 'dotenv/config';
import express from 'express';
import Anthropic from '@anthropic-ai/sdk';
import { fileURLToPath } from 'url';
import { dirname, join } from 'path';

const __dirname = dirname(fileURLToPath(import.meta.url));
const app = express();
const anthropic = new Anthropic({ apiKey: process.env.ANTHROPIC_API_KEY });

const PORT = process.env.PORT || 3000;
const SYSTEM_PROMPT = process.env.PROJECT_CONTEXT ||
  `You are a voice coding assistant accessed from a mobile phone. The user speaks naturally to direct coding tasks.

Guidelines:
- Keep responses concise — they will be read on a small screen and optionally spoken aloud.
- When asked to explain code, be clear and structured.
- When asked to write or refactor code, provide the code with a brief explanation.
- Use plain language; avoid excessive jargon.
- If a request is ambiguous, ask one focused clarifying question.`;

app.use(express.json({ limit: '1mb' }));
app.use(express.static(join(__dirname, 'public')));

// Health check
app.get('/api/health', (_req, res) => {
  res.json({ status: 'ok', model: 'claude-sonnet-4-6' });
});

// Streaming chat endpoint
// Body: { messages: [{role, content}], prompt: string }
app.post('/api/chat', async (req, res) => {
  const { messages = [], prompt } = req.body;

  if (!prompt || typeof prompt !== 'string' || prompt.trim().length === 0) {
    return res.status(400).json({ error: 'prompt is required' });
  }

  if (!process.env.ANTHROPIC_API_KEY) {
    return res.status(500).json({ error: 'ANTHROPIC_API_KEY not configured' });
  }

  // Build message list — append latest user prompt
  const anthropicMessages = [
    ...messages.map(({ role, content }) => ({ role, content })),
    { role: 'user', content: prompt.trim() },
  ];

  // Set up SSE
  res.setHeader('Content-Type', 'text/event-stream');
  res.setHeader('Cache-Control', 'no-cache');
  res.setHeader('Connection', 'keep-alive');
  res.setHeader('X-Accel-Buffering', 'no'); // disable Nginx buffering if proxied
  res.flushHeaders();

  const send = (event, data) => {
    res.write(`event: ${event}\ndata: ${JSON.stringify(data)}\n\n`);
  };

  try {
    const stream = anthropic.messages.stream({
      model: 'claude-sonnet-4-6',
      max_tokens: 2048,
      system: SYSTEM_PROMPT,
      messages: anthropicMessages,
    });

    stream.on('text', (text) => {
      send('delta', { text });
    });

    stream.on('message', (message) => {
      send('done', {
        inputTokens: message.usage?.input_tokens,
        outputTokens: message.usage?.output_tokens,
      });
      res.end();
    });

    stream.on('error', (err) => {
      console.error('Stream error:', err.message);
      send('error', { message: err.message });
      res.end();
    });

    // Clean up if client disconnects early
    req.on('close', () => stream.abort());
  } catch (err) {
    console.error('Chat error:', err.message);
    send('error', { message: err.message });
    res.end();
  }
});

// Catch-all — serve the SPA
app.get('*', (_req, res) => {
  res.sendFile(join(__dirname, 'public', 'index.html'));
});

app.listen(PORT, '0.0.0.0', () => {
  console.log(`\nVoice Coding Agent running at:`);
  console.log(`  Local:   http://localhost:${PORT}`);
  console.log(`  Network: http://<your-local-ip>:${PORT}  ← open on phone\n`);
});
