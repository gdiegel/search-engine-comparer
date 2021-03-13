package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.GoogleSearchResult;
import com.example.searchenginecomparer.entity.SearchResult;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A provider for requests against the Google Custom Search API
 */
public final class GoogleProvider extends ProviderBase<GoogleSearchResult> implements SearchProvider {

    private final String cx;

    /**
     * Constructs a @see {@link GoogleProvider} to make requests against Google's Custom Search JSON API
     * @param uri The @see {@link URI} to use for requests
     * @param apiKey The API key for the Google Search API
     * @param cx The identifier of the Programmable Search Engine
     */
    public GoogleProvider(URI uri, String apiKey, String cx, HttpHandler httpHandler, Deserializer<GoogleSearchResult> deserializer) {
        super(uri, apiKey, httpHandler, deserializer);
        this.cx = cx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult search(String term) {
        final var request = prepareRequest(checkNotNull(term));
        final var response = httpHandler.send(request);
        return deserializer.deserialize(checkNotNull(response).body(), GoogleSearchResult.class);
    }

    @VisibleForTesting
    protected HttpRequest prepareRequest(String term) {
        return HttpRequest.newBuilder()
                .uri(checkNotNull(uri).resolve(String.format("v1?cx=%s&exactTerms=%s&key=%s", checkNotNull(cx), term, checkNotNull(apiKey))))
                .header(ACCEPT_HEADER, MediaType.APPLICATION_JSON.toString())
                .GET()
                .build();
    }
}

