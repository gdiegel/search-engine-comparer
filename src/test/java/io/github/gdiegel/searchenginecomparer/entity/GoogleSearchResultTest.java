package io.github.gdiegel.searchenginecomparer.entity;

import io.github.gdiegel.searchenginecomparer.entity.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static io.github.gdiegel.searchenginecomparer.entity.GoogleSearchResult.Queries;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GoogleSearchResultTest {

    private static final String TERM = "metal";
    private static final long GOOGLE_TOTAL_HITS = 2;
    public static final Queries.Request REQUEST = new Queries.Request(GOOGLE_TOTAL_HITS, TERM);
    public static final Queries QUERIES = new Queries(REQUEST);
    protected static final SearchResult SUT = new GoogleSearchResult(QUERIES);

    @Test
    void shouldBeAbleToGetQueries() {
        assertThat(((GoogleSearchResult) SUT).getQueries()).isEqualTo(QUERIES);
    }

    @Test
    void shouldBeAbleToGetRequest() {
        assertThat(((GoogleSearchResult) SUT).getQueries().getRequest()).containsExactly(REQUEST);
    }

    @Test
    void shouldBeAbleToGetNumberOfHits() {
        assertThat(SUT.getNumberOfHits()).isEqualTo(GOOGLE_TOTAL_HITS);
    }

    @Test
    void shouldBeAbleToGetTerm() {
        assertThat(SUT.getTerm()).isEqualTo(TERM);
    }

    @Test
    void shouldBeAbleToGetEngine() {
        assertThat(SUT.getEngine()).isEqualTo("google");
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenNoRequestInSearchResult() {
        final var queries = new Queries(emptyList());
        final var sut = new GoogleSearchResult(queries);
        assertThatThrownBy(sut::getRequest)
            .isExactlyInstanceOf(InvalidDataException.class)
            .hasMessage("Couldn't find single request element in response");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRequestisNull() {
        final var queries = new Queries(singletonList(null));
        final var sut = new GoogleSearchResult(queries);
        assertThatThrownBy(sut::getRequest)
            .isExactlyInstanceOf(NullPointerException.class);
    }
}