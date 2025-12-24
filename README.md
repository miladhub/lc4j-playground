# Langchain4j

# With LM Studio

https://docs.langchain4j.dev/integrations/language-models/local-ai/
https://github.com/langchain4j/langchain4j/blob/main/langchain4j-local-ai/src/test/java/dev/langchain4j/model/localai/LocalAiChatModelIT.java
https://github.com/langchain4j/langchain4j/issues/2758
https://github.com/YorkieDev/lmstudioservercodeexamples?tab=readme-ov-file#hello-world-curl
https://github.com/lmstudio-ai/lmstudio-bug-tracker/issues/1079

```bash
nghttpx -f"0.0.0.0,12345;no-tls" -b"127.0.0.1,1234;;no-tls"
```

```bash
curl --http2 http://localhost:12345/v1/chat/completions \
  -H "Content-Type: application/json" \
  -d '{
    "model": "model-identifier",
    "messages": [
      { "role": "system", "content": "Always answer in rhymes." },
      { "role": "user", "content": "Introduce yourself." }
    ],
    "temperature": 0.7,
    "max_tokens": -1,
    "stream": true
}'
```

curl --http2 http://localhost:12345/v1/responses \
  -H "Content-Type: application/json" \
  -d '{
    "model": "gpt-5-nano",
    "input": [
      {"role": "user", "content": "Make my name cactus-like"}
    ],
    "tools": [
      {
        "name": "cactify_name",
        "type": "function",
        "description": "Transforms a name into a cactus-themed version",
        "parameters": {
          "type": "object",
          "properties": {
            "name": {
              "type": "string",
              "description": "Name to cactus-ify"
            }
          },
          "required": ["name"]
        }
      }
    ]
  }'

# With Ollama

https://docs.langchain4j.dev/integrations/language-models/openai-compatible/#ollama
https://ollama.com/library/functiongemma
https://github.com/langchain4j/langchain4j-examples/blob/main/ollama-examples/src/main/java/OllamaChatModelTest.java

```bash
brew install ollama
# brew services start ollama
ollama serve
```

```bash
ollama pull functiongemma
```
