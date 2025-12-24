package org.example;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.AiServices;

public class OpenAiMain
{
    record Person(String name, int age, double height, boolean married) {}

    interface PersonExtractor {
        Person extractPersonFrom(String text);
    }

    public static void main(String[] args) {
        ChatModel model =
                new OpenAiChatModel.OpenAiChatModelBuilder()
                        .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                        .apiKey(System.getenv("OPENAI_API_KEY"))
                        .responseFormat("json_schema")
                        .strictJsonSchema(true)
                        .logRequests(true)
                        .logResponses(true)
                        .build();

        PersonExtractor personExtractor =
                AiServices.create(PersonExtractor.class, model);

        String text = """
        John is 42 years old and lives an independent life.
        He stands 1.75 meters tall and carries himself with confidence.
        Currently unmarried, he enjoys the freedom to focus on his personal goals and interests.
        """;

        var person = personExtractor.extractPersonFrom(text);

        System.out.println(person);
    }
}