package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.GoogleSearchResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static com.example.searchenginecomparer.adapter.ProviderBase.ACCEPT_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class GoogleProviderTest extends ProviderTest {

    protected static final Deserializer<GoogleSearchResult> DESERIALIZER = (Deserializer<GoogleSearchResult>) mock(Deserializer.class);
    protected static final String CX = "def";

    @BeforeAll
    static void init() {
        when(HTTP_HANDLER.send(Mockito.any())).thenReturn(RESPONSE);
        when(RESPONSE.body()).thenReturn(BODY);
        when(DESERIALIZER.deserialize(BODY, GoogleSearchResult.class)).thenReturn((GoogleSearchResult) GOOGLE_SEARCH_RESULT);
    }

    @Test
    void shouldBeAbleToGetSearchResult() {
        final var sut = new GoogleProvider(URI, API_KEY, GoogleProviderTest.CX, HTTP_HANDLER, DESERIALIZER);
        final var searchResult = sut.search("metal");
        assertThat(searchResult.getEngine()).isEqualTo(GOOGLE_SEARCH_RESULT.getEngine());
        assertThat(searchResult.getNumberOfHits()).isEqualTo(GOOGLE_SEARCH_RESULT.getNumberOfHits());
        assertThat(searchResult.getTerm()).isEqualTo(GOOGLE_SEARCH_RESULT.getTerm());
    }

    @Test
    void shouldCreateValidHttpRequest() {
        final var sut = new GoogleProvider(URI, API_KEY, GoogleProviderTest.CX, HTTP_HANDLER, DESERIALIZER);
        final var httpRequest = sut.prepareRequest(TERM);
        assertThat(httpRequest.uri()).hasHost(URI.getHost());
        assertThat(httpRequest.uri()).hasQuery(String.format("cx=%s&exactTerms=%s&key=%s", CX, TERM, API_KEY));
        assertThat(httpRequest.uri()).hasPath("/v1");
        assertThat(httpRequest.method()).isEqualTo("GET");
        assertThat(httpRequest.headers().allValues(ACCEPT_HEADER)).contains(MediaType.APPLICATION_JSON.toString());
    }

}