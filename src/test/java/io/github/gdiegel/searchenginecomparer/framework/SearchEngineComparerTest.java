package io.github.gdiegel.searchenginecomparer.framework;

import io.github.gdiegel.searchenginecomparer.entity.BingSearchResult;
import io.github.gdiegel.searchenginecomparer.entity.GoogleSearchResult;
import io.github.gdiegel.searchenginecomparer.entity.SearchResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchEngineComparerTest {

    protected static final String TERM_A = "black";
    protected static final String TERM_B = "metal";
    protected static final long GOOGLE_TOTAL_HITS_A = 2;
    protected static final long GOOGLE_TOTAL_HITS_B = 5;
    protected static final long BING_TOTAL_HITS_A = 3;
    protected static final long BING_TOTAL_HITS_B = 7;
    protected static final SearchResult GOOGLE_SEARCH_RESULT_A = new GoogleSearchResult(new GoogleSearchResult.Queries(new GoogleSearchResult.Queries.Request(GOOGLE_TOTAL_HITS_A, TERM_A)));
    protected static final SearchResult GOOGLE_SEARCH_RESULT_B = new GoogleSearchResult(new GoogleSearchResult.Queries(new GoogleSearchResult.Queries.Request(GOOGLE_TOTAL_HITS_B, TERM_B)));
    protected static final SearchResult BING_SEARCH_RESULT_A = new BingSearchResult(new BingSearchResult.QueryContext(TERM_A), new BingSearchResult.Webpages(BING_TOTAL_HITS_A));
    protected static final SearchResult BING_SEARCH_RESULT_B = new BingSearchResult(new BingSearchResult.QueryContext(TERM_B), new BingSearchResult.Webpages(BING_TOTAL_HITS_B));
    protected static final List<SearchResult> RESULTS = List.of(GOOGLE_SEARCH_RESULT_A, GOOGLE_SEARCH_RESULT_B, BING_SEARCH_RESULT_A, BING_SEARCH_RESULT_B);

    @Test
    void ShouldCalculateWinnerCorrectly() {
        assertThat(SearchEngineComparer.determineWinner(RESULTS, "google")).isEqualTo(TERM_B);

    }

    @Test
    void determineTotalWinner() {
        assertThat(SearchEngineComparer.determineTotalWinner(RESULTS)).isEqualTo(TERM_B);
    }
}