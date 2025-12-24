package org.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.http.client.jdk.JdkHttpClient;
import dev.langchain4j.http.client.jdk.JdkHttpClientBuilder;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.net.http.HttpClient;

public class LMStudioMain
{
    public static final String SYS_PROMPT =
            """
            When a task requires name transformation,
            you MUST call the appropriate tool.
            """;

    interface Assistant
    {
        String chat(String question);
    }

    static class Tools
    {
        @Tool("Transforms a name into a cactus-themed version")
        public String cactifyName(String name) {
            return "🌵 " + name.replaceAll("[aeiouAEIOU]", "i") + " 🌵";
        }
    }

    public static void main(String[] args) {
        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1);

        JdkHttpClientBuilder jdkHttpClientBuilder = JdkHttpClient.builder()
                .httpClientBuilder(httpClientBuilder);

        ChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://127.0.0.1:1234/v1")
                .modelName("functiongemma-270m-it")
                .httpClientBuilder(jdkHttpClientBuilder)
                .temperature(0.0)
                .topP(1.0)
                .logRequests(true)
                .logResponses(true)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .systemMessageProvider(id -> SYS_PROMPT)
                .tools(new Tools())
                .build();

        String result = assistant.chat(
                "Make my name cactus-like. My name is Foo.");

        System.out.println(result);
    }
}
