package de.leanovate.router.vertx;

import de.leanovate.router.RequestAdapter;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

import java.util.Optional;

/**
 * Adapts the Vert.X variant of a http request/response.
 */
public class VertxServerRequestAdapter implements RequestAdapter<HttpServerRequest, HttpServerResponse> {
    private final HttpServerRequest request;

    public VertxServerRequestAdapter(final HttpServerRequest request) {

        this.request = request;
    }

    @Override
    public String getPath() {

        return request.path();
    }

    @Override
    public String getMethod() {

        return request.method();
    }

    @Override
    public Optional<String> getQueryParam(final String name) {

        return Optional.ofNullable(request.params().get(name));
    }

    @Override
    public HttpServerRequest getRequest() {

        return request;
    }

    @Override
    public HttpServerResponse getResponse() {

        return request.response();
    }
}
