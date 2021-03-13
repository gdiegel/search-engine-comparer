package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.exception.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@inheritDoc}
 */
public class MappingDeserializer<T> implements Deserializer<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(MappingDeserializer.class);

    public MappingDeserializer() {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * De-serialize a string using the Jackson object mapper
     *
     * @param json the string representing the JSON structure to de-serialize
     * @param type the type of the object which the JSON string should be de-serialized into
     * @return An object of type T
     */
    @Override
    public T deserialize(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(checkNotNull(json), checkNotNull(type));
        } catch (JsonProcessingException e) {
            final var message = "Couldn't de-serialize JSON";
            LOG.debug(message, e);
            throw new InvalidDataException(message, e);
        }
    }
}