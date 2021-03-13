package com.example.searchenginecomparer.entity;

import com.example.searchenginecomparer.entity.exception.InvalidDataException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation for a Google search result. Used for de-serialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class GoogleSearchResult implements SearchResult {

    private static final Logger LOG = LoggerFactory.getLogger(GoogleSearchResult.class);
    private static final String NAME = "google";
    @JsonProperty("queries")
    private Queries queries;

    public GoogleSearchResult() {
    }

    @VisibleForTesting
    public GoogleSearchResult(Queries queries) {
        this.queries = queries;
    }

    public Queries getQueries() {
        return this.queries;
    }

    public void setQueries(Queries queries) {
        this.queries = queries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNumberOfHits() {
        return checkNotNull(getRequest()).getTotalResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTerm() {
        return checkNotNull(getRequest()).getExactTerms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEngine() {
        return NAME;
    }

    @VisibleForTesting
    protected Queries.Request getRequest() {
        final var request = checkNotNull(getQueries()).getRequest();
        final var first = checkNotNull(request).stream().findFirst();
        if (first.isEmpty()) {
            final var message = "Couldn't find single request element in response";
            LOG.debug(message);
            throw new InvalidDataException(message);
        }
        return checkNotNull(request.get(0));
    }

    public static class Queries {

        @JsonProperty("request")
        private List<Request> request;

        public Queries() {
        }

        @VisibleForTesting
        public Queries(Request request) {
            this.request = Collections.singletonList(request);
        }

        public Queries(List<Request> request) {
            this.request = request;
        }

        public List<Request> getRequest() {
            return this.request;
        }

        public void setRequest(List<Request> request) {
            this.request = request;
        }

        public static class Request {

            @JsonProperty("totalResults")
            private long totalResults;
            @JsonProperty("exactTerms")
            private String exactTerms;

            public Request() {
            }

            @VisibleForTesting
            public Request(long totalResults, String exactTerms) {
                this.totalResults = totalResults;
                this.exactTerms = exactTerms;
            }

            public long getTotalResults() {
                return totalResults;
            }

            public void setTotalResults(long totalResults) {
                this.totalResults = totalResults;
            }

            public String getExactTerms() {
                return exactTerms;
            }

            public void setExactTerms(String exactTerms) {
                this.exactTerms = exactTerms;
            }

        }
    }

}
