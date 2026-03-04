# Voice Coding Agent — Setup Guide

A mobile-first voice interface for directing Claude from your phone browser.

## Prerequisites

- Node.js 18+
- An Anthropic API key — https://console.anthropic.com
- Phone and laptop on the **same Wi-Fi network**

---

## Quick Start

```bash
# 1. Enter the project directory
cd voice-agent

# 2. Install dependencies
npm install

# 3. Create your .env file
cp .env.example .env
# Edit .env and paste your ANTHROPIC_API_KEY

# 4. Start the server
npm start
```

The terminal will print two URLs:

```
Voice Coding Agent running at:
  Local:   http://localhost:3000
  Network: http://192.168.x.x:3000  ← open this on your phone
```

Open the **Network URL** in your phone's browser (Chrome on Android or Safari on iOS).

---

## Usage

| Action | How |
|---|---|
| **Speak a request** | Hold the mic button → speak → release |
| **Desktop testing** | Hold mouse button on mic, or hold **Space bar** |
| **Stop a streaming response** | Tap **Stop** (appears during streaming) |
| **Toggle text-to-speech** | Tap the 🔊 button in the header |
| **Clear conversation** | Tap the 🗑 button in the header |

### Example voice prompts

- *"Explain what the auth module does"*
- *"Refactor the login function to use async/await"*
- *"What's a better algorithm for this sorting logic?"*
- *"Suggest error handling for the payment service"*
- *"Write a unit test for the validateUser function"*

---

## Browser Support

| Browser | Speech Recognition | TTS |
|---|---|---|
| Chrome (Android) | ✅ | ✅ |
| Safari (iOS 17+) | ✅ | ✅ |
| Firefox | ❌ | ✅ |
| Chrome (desktop) | ✅ | ✅ |

> iOS Safari requires the page to be served over HTTPS **or** localhost.
> Because you're accessing via local IP, use Chrome on Android for the smoothest experience, or set up a self-signed certificate + `https` if needed on iOS.

---

## Configuration

Edit `.env` to customise:

```env
ANTHROPIC_API_KEY=sk-ant-...
PORT=3000

# Inject project context into every system prompt
PROJECT_CONTEXT=You are a coding assistant for a Node.js/TypeScript project. ...
```

---

## Project Structure

```
voice-agent/
├── server.js          # Express backend — Anthropic streaming over SSE
├── package.json
├── .env               # Your secrets (git-ignored)
├── .env.example
└── public/
    ├── index.html     # Mobile UI shell
    ├── style.css      # Dark, phone-optimised styles
    └── app.js         # Web Speech API + SSE client
```

---

## HTTPS on iOS (optional)

If you need HTTPS for iOS Safari, the easiest path is [mkcert](https://github.com/FiloSottile/mkcert):

```bash
brew install mkcert
mkcert -install
mkcert localhost 192.168.x.x   # your local IP

# Then update server.js to use https.createServer({ key, cert }, app)
```
