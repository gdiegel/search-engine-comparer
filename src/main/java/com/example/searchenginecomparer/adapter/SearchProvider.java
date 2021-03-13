package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.SearchResult;

/**
 * Contract for a search provider
 */
public interface SearchProvider {

    /**
     * Search for a given term and return a typed @see {@link SearchResult}
     * @param term The search term to use
     * @return An instance of @see {@link SearchResult}
     */
    SearchResult search(String term);
}
