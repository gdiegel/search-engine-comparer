package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.adapter.exception.InvalidResponseException;
import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A response validating handler for HTTP requests
 */
public class ValidatingHttpHandler implements HttpHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatingHttpHandler.class);
    private static final HttpResponse.BodyHandler<String> BODY_HANDLER = HttpResponse.BodyHandlers.ofString();

    private final HttpClient client;

    public ValidatingHttpHandler(HttpClient client) {
        this.client = client;
    }

    /**
     * Sends a prepared @see {@link HttpRequest}
     * @param request the request to send
     * @return the @see {@link HttpResponse<String>}
     */
    @Override
    public HttpResponse<String> send(HttpRequest request) {
        final var response = doSend(checkNotNull(request));
        validateResponseIsSuccessful(response);
        return response;
    }

    @VisibleForTesting
    protected void validateResponseIsSuccessful(HttpResponse<String> response) {
        if (checkNotNull(response).statusCode() >= 400) {
            final var message = "Unsuccessful response";
            LOG.debug(message);
            throw new InvalidResponseException(message);
        }
    }

    private HttpResponse<String> doSend(HttpRequest request) {
        try {
            return checkNotNull(client).send(request, BODY_HANDLER);
        } catch (IOException | InterruptedException e) {
            final var message = "Couldn't get response";
            LOG.debug(message, e);
            throw new InvalidResponseException(message, e);
        }
    }

}