package io.github.gdiegel.searchenginecomparer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation for a Bing search result. Used for de-serialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class BingSearchResult implements SearchResult {

    private static final String NAME = "bing";

    @JsonProperty("queryContext")
    private QueryContext queryContext;

    @JsonProperty("webPages")
    private Webpages webpages;

    public BingSearchResult() {
    }

    @VisibleForTesting
    public BingSearchResult(QueryContext queryContext, Webpages webpages) {
        this.queryContext = queryContext;
        this.webpages = webpages;
    }

    public QueryContext getQueryContext() {
        return queryContext;
    }

    public void setQueryContext(QueryContext queryContext) {
        this.queryContext = queryContext;
    }

    public Webpages getWebpages() {
        return this.webpages;
    }

    public void setWebpages(Webpages queries) {
        this.webpages = queries;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getNumberOfHits() {
        return checkNotNull(getWebpages()).getTotalEstimatedMatches();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTerm() {
        return checkNotNull(getQueryContext()).getOriginalQuery();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEngine() {
        return NAME;
    }

    public static class QueryContext {

        @JsonProperty("originalQuery")
        private String originalQuery;

        public QueryContext() {
        }

        @VisibleForTesting
        public QueryContext(String originalQuery) {
            this.originalQuery = originalQuery;
        }

        public String getOriginalQuery() {
            return originalQuery;
        }

        public void setOriginalQuery(String originalQuery) {
            this.originalQuery = originalQuery;
        }

    }

    public static class Webpages {

        @JsonProperty("totalEstimatedMatches")
        private long totalEstimatedMatches;

        public Webpages() {
        }

        @VisibleForTesting
        public Webpages(long totalEstimatedMatches) {
            this.totalEstimatedMatches = totalEstimatedMatches;
        }

        public long getTotalEstimatedMatches() {
            return totalEstimatedMatches;
        }

        public void setTotalEstimatedMatches(long totalEstimatedMatches) {
            this.totalEstimatedMatches = totalEstimatedMatches;
        }

    }

}
