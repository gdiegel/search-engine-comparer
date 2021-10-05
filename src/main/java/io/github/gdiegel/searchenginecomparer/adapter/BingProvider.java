package io.github.gdiegel.searchenginecomparer.adapter;

import com.google.common.annotations.VisibleForTesting;
import io.github.gdiegel.searchenginecomparer.entity.BingSearchResult;
import io.github.gdiegel.searchenginecomparer.entity.SearchResult;
import org.springframework.http.MediaType;

import java.net.URI;
import java.net.http.HttpRequest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A provider for requests against the Bing Web Search API
 */
public final class BingProvider extends ProviderBase<BingSearchResult> implements SearchProvider {

    protected static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";

    /**
     * Constructs a @see {@link BingProvider} to make requests against Microsoft's Bing Web Search API
     *
     * @param uri The @see {@link URI} to use for requests
     * @param apiKey the API key for the Bing Web Search API
     */
    public BingProvider(URI uri, String apiKey, HttpHandler httpHandler, Deserializer<BingSearchResult> deserializer) {
        super(uri, apiKey, httpHandler, deserializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResult search(String term) {
        final var request = prepareRequest(checkNotNull(term));
        final var response = httpHandler.send(request);
        return deserializer.deserialize(checkNotNull(response).body(), BingSearchResult.class);
    }

    @VisibleForTesting
    protected HttpRequest prepareRequest(String term) {
        return HttpRequest.newBuilder()
            .uri(checkNotNull(uri).resolve(String.format("search?q=%s", term)))
            .header(ACCEPT_HEADER, MediaType.APPLICATION_JSON.toString())
            .header(OCP_APIM_SUBSCRIPTION_KEY, checkNotNull(apiKey))
            .GET()
            .build();
    }
}

