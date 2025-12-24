package org.example;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonTest
{
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Foo bar = new Bar("Hello");
        Foo baz = new Baz(42);

        // Serialize
        String barJson = mapper.writeValueAsString(bar);
        String bazJson = mapper.writeValueAsString(baz);

        System.out.println(barJson); // {"type":"Bar","x":"Hello"}
        System.out.println(bazJson); // {"type":"Baz","y":42}

        // Deserialize
        Foo deserializedBar = mapper.readValue(barJson, Foo.class);
        Foo deserializedBaz = mapper.readValue(bazJson, Foo.class);

        System.out.println(deserializedBar); // Bar[x=Hello]
        System.out.println(deserializedBaz); // Baz[y=42]
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Bar.class, name = "Bar"),
            @JsonSubTypes.Type(value = Baz.class, name = "Baz")
    })
    interface Foo {}
    record Bar(String x) implements Foo {}
    record Baz(int y) implements Foo {}
}
