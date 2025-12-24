package org.example;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;

public class OllamaMain
{
    record Person(String name, int age, double height, boolean married) {
    }

    interface PersonExtractor {
        @SystemMessage("You are a people creator.")
        @UserMessage("Create a random person.")
        Person createRandomPerson();
    }

    record Result(String name) {}

    public static final String SYS_PROMPT =
            """
            When a task requires name transformation,
            you MUST call the appropriate tool.
            """;

    interface Assistant
    {
        Result chat(String question);
    }

    static class Tools
    {
        @Tool("Transforms a name into a cactus-themed version")
        public String cactifyName(String name) {
            System.out.println("calling function with arg " + name);
            return "🌵 " + name.replaceAll("[aeiouAEIOU]", "i") + " 🌵";
        }
    }

    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://127.0.0.1:11434/v1")
                .modelName("functiongemma")
                .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
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

        var result = assistant.chat(
                "Make my name cactus-like. My name is Foo.");

        System.out.println(result);

        PersonExtractor personExtractor =
                AiServices.create(PersonExtractor.class, model);

        var person = personExtractor.createRandomPerson();

        System.out.println(person);
    }
}
