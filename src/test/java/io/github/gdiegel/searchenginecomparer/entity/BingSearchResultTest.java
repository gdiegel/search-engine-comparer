package io.github.gdiegel.searchenginecomparer.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BingSearchResultTest {

    private static final String TERM = "metal";
    private static final long BING_TOTAL_HITS = 3;
    private static final BingSearchResult.Webpages WEBPAGES = new BingSearchResult.Webpages(BING_TOTAL_HITS);
    private static final BingSearchResult.QueryContext QUERY_CONTEXT = new BingSearchResult.QueryContext(TERM);
    private static final SearchResult SUT = new BingSearchResult(QUERY_CONTEXT, WEBPAGES);

    @Test
    void shouldBeAbleToGetQueryContext() {
        assertThat(((BingSearchResult) SUT).getQueryContext()).isEqualTo(QUERY_CONTEXT);
    }

    @Test
    void shouldBeAbleToGetWebpages() {
        assertThat(((BingSearchResult) SUT).getWebpages()).isEqualTo(WEBPAGES);
    }

    @Test
    void shouldBeAbleToGetNumberOfHits() {
        assertThat(SUT.getNumberOfHits()).isEqualTo(BING_TOTAL_HITS);
    }

    @Test
    void shouldBeAbleToGetTerm() {
        assertThat(SUT.getTerm()).isEqualTo(TERM);
    }

    @Test
    void shouldBeAbleToGetEngine() {
        assertThat(SUT.getEngine()).isEqualTo("bing");
    }
}