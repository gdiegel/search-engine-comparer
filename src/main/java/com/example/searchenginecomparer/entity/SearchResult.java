package com.example.searchenginecomparer.entity;

import org.springframework.util.StringUtils;

public interface SearchResult {

    /**
     * Return the total number of hits contained in this @see {@link SearchResult}
     *
     * @return A long representing the total number of hits
     */
    long getNumberOfHits();

    /**
     * Return the search term that produced this @see {@link SearchResult}
     *
     * @return A string representing the search term
     */
    String getTerm();

    /**
     * The name of the search engine that was invoked
     *
     * @return A string representing the name of the search engine
     */
    String getEngine();

    default String format() {
        return String.format("%s: %d", StringUtils.capitalize(getEngine()), getNumberOfHits());
    }

}
