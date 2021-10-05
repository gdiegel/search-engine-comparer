package io.github.gdiegel.searchenginecomparer.entity;

import org.junit.jupiter.api.Test;

import static io.github.gdiegel.searchenginecomparer.entity.BingSearchResult.QueryContext;
import static io.github.gdiegel.searchenginecomparer.entity.BingSearchResult.Webpages;
import static io.github.gdiegel.searchenginecomparer.entity.GoogleSearchResult.Queries;
import static io.github.gdiegel.searchenginecomparer.entity.GoogleSearchResult.Queries.Request;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchResultTest {

    protected static final String TERM = "black";
    protected static final long TOTAL_HITS = 2;
    protected static final SearchResult GOOGLE_SEARCH_RESULT = new GoogleSearchResult(new Queries(new Request(TOTAL_HITS, TERM)));
    protected static final SearchResult BING_SEARCH_RESULT = new BingSearchResult(new QueryContext(TERM), new Webpages(TOTAL_HITS));

    @Test
    void ShouldDefineSearchResultAsInterface() {
        assertThat(SearchResult.class).isInterface();
    }

    @Test
    void ShouldBeAbleToFormatGoogleSearchResult() {
        assertThat(GOOGLE_SEARCH_RESULT.format()).isEqualTo("Google: 2");
    }

    @Test
    void ShouldBeAbleToFormatBingSearchResult() {
        assertThat(BING_SEARCH_RESULT.format()).isEqualTo("Bing: 2");
    }

}
