package io.github.gdiegel.searchenginecomparer.adapter;

/**
 * A generic deserializer
 * @param <T> The value type of the object which the json string should be de-serialized to
 */
public interface Deserializer<T> {

    /**
     * Deserialize the given string into an object of type T
     *
     * @param json the string representing the JSON structure to de-serialize
     * @param type the type of the object which the string should be de-serilized into
     * @return An object of type T
     */
    T deserialize(String json, Class<T> type);
}
