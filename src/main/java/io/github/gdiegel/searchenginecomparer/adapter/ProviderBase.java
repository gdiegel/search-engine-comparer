package io.github.gdiegel.searchenginecomparer.adapter;

import io.github.gdiegel.searchenginecomparer.entity.SearchResult;

import java.net.URI;

/**
 * Base class for a search provider. Extend this class to add a new provider implementation.
 *
 * @param <T> The type of @see {@link SearchResult} that the provider implementation will
 * use to the search API's response.
 */
abstract class ProviderBase<T> {

    protected static final String ACCEPT_HEADER = "Accept";

    protected final URI uri;
    protected final String apiKey;
    protected final HttpHandler httpHandler;
    protected final Deserializer<T> deserializer;

    protected ProviderBase(URI uri, String apiKey, HttpHandler httpHandler, Deserializer<T> deserializer) {
        this.uri = uri;
        this.apiKey = apiKey;
        this.httpHandler = httpHandler;
        this.deserializer = deserializer;
    }

}
