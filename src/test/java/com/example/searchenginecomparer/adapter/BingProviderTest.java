package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.BingSearchResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static com.example.searchenginecomparer.adapter.BingProvider.OCP_APIM_SUBSCRIPTION_KEY;
import static com.example.searchenginecomparer.adapter.ProviderBase.ACCEPT_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class BingProviderTest extends ProviderTest {

    protected static final Deserializer<BingSearchResult> DESERIALIZER = (Deserializer<BingSearchResult>) mock(Deserializer.class);

    @BeforeAll
    static void init() {
        when(HTTP_HANDLER.send(Mockito.any())).thenReturn(RESPONSE);
        when(RESPONSE.body()).thenReturn(BODY);
        when(DESERIALIZER.deserialize(BODY, BingSearchResult.class)).thenReturn((BingSearchResult) BING_SEARCH_RESULT);
    }

    @Test
    void shouldBeAbleToGetSearchResult() {
        final var sut = new BingProvider(URI, API_KEY, HTTP_HANDLER, DESERIALIZER);
        final var searchResult = sut.search("metal");
        assertThat(searchResult.getEngine()).isEqualTo(BING_SEARCH_RESULT.getEngine());
        assertThat(searchResult.getNumberOfHits()).isEqualTo(BING_SEARCH_RESULT.getNumberOfHits());
        assertThat(searchResult.getTerm()).isEqualTo(BING_SEARCH_RESULT.getTerm());
    }

    @Test
    void shouldCreateValidHttpRequest() {
        final var sut = new BingProvider(URI, API_KEY, HTTP_HANDLER, DESERIALIZER);
        final var httpRequest = sut.prepareRequest(TERM);
        assertThat(httpRequest.uri()).hasHost(URI.getHost());
        assertThat(httpRequest.uri()).hasQuery(String.format("q=%s", TERM));
        assertThat(httpRequest.uri()).hasPath("/search");
        assertThat(httpRequest.method()).isEqualTo("GET");
        assertThat(httpRequest.headers().allValues(ACCEPT_HEADER)).contains(MediaType.APPLICATION_JSON.toString());
        assertThat(httpRequest.headers().allValues(OCP_APIM_SUBSCRIPTION_KEY)).contains(API_KEY);
    }

}