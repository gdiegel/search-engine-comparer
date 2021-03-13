package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.entity.BingSearchResult;
import com.example.searchenginecomparer.entity.GoogleSearchResult;
import com.example.searchenginecomparer.entity.GoogleSearchResult.Queries.Request;
import com.example.searchenginecomparer.entity.SearchResult;

import java.net.URI;
import java.net.http.HttpResponse;

import static com.example.searchenginecomparer.entity.BingSearchResult.QueryContext;
import static com.example.searchenginecomparer.entity.BingSearchResult.Webpages;
import static com.example.searchenginecomparer.entity.GoogleSearchResult.Queries;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
public abstract class ProviderTest {

    protected static final String TERM = "metal";
    protected static final long GOOGLE_TOTAL_HITS = 2;
    protected static final long BING_TOTAL_HITS = 3;
    protected static final SearchResult GOOGLE_SEARCH_RESULT = new GoogleSearchResult(new Queries(new Request(GOOGLE_TOTAL_HITS, TERM)));
    protected static final SearchResult BING_SEARCH_RESULT = new BingSearchResult(new QueryContext(TERM), new Webpages(BING_TOTAL_HITS));
    protected static final String API_KEY = "abc";
    protected static final URI URI = java.net.URI.create("https://example.com/");
    protected static final String BODY = "{\"black\":\"metal\"}";
    protected static final HttpHandler HTTP_HANDLER = mock(HttpHandler.class);
    protected static final HttpResponse<String> RESPONSE = (HttpResponse<String>) mock(HttpResponse.class);

}
