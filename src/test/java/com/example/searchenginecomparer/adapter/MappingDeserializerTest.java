package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.exception.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MappingDeserializerTest {

    private static final String VALID_JSON = "{\"name\":\"Nodfyr\"}";
    private static final String INVALID_JSON = "{name:\"Nodfyr\"}";
    private static final Deserializer<Person> SUT = new MappingDeserializer<>();

    @Test
    void ShouldBeAbleToDeserializeValidJson() {
        final var person = SUT.deserialize(VALID_JSON, Person.class);
        assertThat(person).isNotNull();
        assertThat(person.name()).isEqualTo("Nodfyr");
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenJsonIsInvalid() {
        assertThatThrownBy(() -> SUT.deserialize(INVALID_JSON, Person.class))
                .isExactlyInstanceOf(InvalidDataException.class)
                .hasCauseInstanceOf(JsonProcessingException.class)
                .hasMessage("Couldn't de-serialize JSON");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenParameterIsNull() {
        assertThatThrownBy(() -> SUT.deserialize(null, Person.class))
                .isExactlyInstanceOf(NullPointerException.class);
    }

}