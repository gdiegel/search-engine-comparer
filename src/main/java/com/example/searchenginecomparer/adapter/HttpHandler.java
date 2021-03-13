package com.example.searchenginecomparer.adapter;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * A handler for HTTP requests
 */
public interface HttpHandler {

    /**
     * Send the request and return a typed @see {@link HttpResponse<String>}
     * @param request the @see {@link HttpRequest} to send
     * @return A @see {@link HttpResponse<String>}
     */
    HttpResponse<String> send(HttpRequest request);
}
