package com.example.searchenginecomparer.adapter;

import com.example.searchenginecomparer.adapter.exception.InvalidResponseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class ValidatingHttpHandlerTest {

    private static final HttpClient CLIENT = mock(HttpClient.class);
    private static final HttpResponse<String> RESPONSE = (HttpResponse<String>) mock(HttpResponse.class);
    private static final HttpHandler SUT = new ValidatingHttpHandler(CLIENT);
    private static final HttpRequest REQUEST = HttpRequest.newBuilder()
            .uri(URI.create("http://example.com"))
            .GET()
            .build();

    @Test
    void shouldThrowNullPointerExceptionWhenCallingSendWithNullRequest() {
        assertThatThrownBy(() -> SUT.send(null)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldBeAbleToValidateCLientErrorResponse() {
        when(RESPONSE.statusCode()).thenReturn(400);
        assertThatThrownBy(() -> ((ValidatingHttpHandler) SUT).validateResponseIsSuccessful(RESPONSE)).isExactlyInstanceOf(InvalidResponseException.class);
    }

    @Test
    void shouldBeAbleToValidateServerErrorResponse() {
        when(RESPONSE.statusCode()).thenReturn(500);
        assertThatThrownBy(() -> ((ValidatingHttpHandler) SUT).validateResponseIsSuccessful(RESPONSE)).isExactlyInstanceOf(InvalidResponseException.class);
    }

    @Test
    void shouldCatchIoExceptionAndWrapInInvalidResponseException() throws IOException, InterruptedException {
        when(CLIENT.send(Mockito.any(), Mockito.any())).thenThrow(IOException.class);
        assertThatThrownBy(() -> SUT.send(REQUEST))
                .isExactlyInstanceOf(InvalidResponseException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasMessage("Couldn't get response");
    }

}