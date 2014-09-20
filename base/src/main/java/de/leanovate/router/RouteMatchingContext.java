package de.leanovate.router;

import java.util.Optional;

/**
 * Generic context used for matching a request to a route.
 *
 * @param <Q> the http request of the underlying framework
 * @param <R> the http response of the underlying framework
 */
public class RouteMatchingContext<Q, R> {
    /**
     * The remaining path segment in this context that still needs to be matched.
     */
    public final String path;

    /**
     * The http method of the request.
     */
    public final String method;

    /**
     * Low level request/response adapter of the underlying framework.
     */
    private final RequestAdapter<Q, R> adapter;

    public RouteMatchingContext(final String path, final String method, final RequestAdapter<Q, R> adapter) {

        this.path = path;
        this.method = method;
        this.adapter = adapter;
    }

    /**
     * Create a sub context with new remaining path.
     */
    public RouteMatchingContext<Q, R> withPath(final String newPath) {

        return new RouteMatchingContext<>(newPath, method, this.adapter);
    }

    /**
     * Get an optional query parameter of the http request parameter.
     */
    public Optional<String> getQueryParam(String name) {

        return adapter.getQueryParam(name);
    }

    /**
     * Get the the http request object of the underlying framework.
     */
    public Q getRequest() {

        return adapter.getRequest();
    }

    /**
     * Get the the http response object of the underlying framework.
     */
    public R getResponse() {

        return adapter.getResponse();
    }
}
