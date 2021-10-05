package io.github.gdiegel.searchenginecomparer.adapter;

import io.github.gdiegel.searchenginecomparer.adapter.exception.InvalidResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = 8787)
class ValidatingHttpHandlerIT {
    public static final int PORT = 8787;
    public static final String SUCCESS_PATH = "greeting";
    public static final String SUCCESS_BODY = "{Hello, World!}";
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final String FAILURE_PATH = "explode";
    public static final String FAILURE_BODY = "{Boom!}";
    public static final int FAILURE_STATUS_CODE = 400;
    public static final HttpClient realHttpClient = HttpClient.newBuilder().build();
    public static final HttpHandler SUT = new ValidatingHttpHandler(realHttpClient);
    @SuppressWarnings("FieldCanBeLocal")
    private ClientAndServer client;

    @BeforeEach
    public void beforeEachLifecyleMethod(ClientAndServer client) {
        this.client = client;
    }

    @Test
    void ShouldBeAbleToSendSuccessfullRequest() {
        new MockServerClient("localhost", PORT)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/" + SUCCESS_PATH)
            )
            .respond(
                response()
                    .withStatusCode(SUCCESS_STATUS_CODE)
                    .withBody(SUCCESS_BODY)
            );

        final var request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:%d/%s", 8787, SUCCESS_PATH)))
            .GET()
            .build();
        final var response = SUT.send(request);
        assertThat(response).isNotNull();
        assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS_CODE);
        assertThat(response.body()).isEqualTo(SUCCESS_BODY);
    }

    @Test
    void ShouldBeThrowInvalidResponseExceptionOnUnsuccessfullRequest() {
        new MockServerClient("localhost", PORT)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/" + FAILURE_PATH)
            )
            .respond(
                response()
                    .withStatusCode(FAILURE_STATUS_CODE)
                    .withBody(FAILURE_BODY)
            );

        final var request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://localhost:%d/%s", 8787, FAILURE_PATH)))
            .GET()
            .build();
        assertThatThrownBy(() -> SUT.send(request))
            .isExactlyInstanceOf(InvalidResponseException.class)
            .hasMessage("Unsuccessful response");
    }

}