package de.leanovate.router;

import java.util.Optional;

public class RouteMatchingContext<Q, R> {
    public final String path;

    public final String method;

    private final RequestAdapter<Q, R> adapter;

    public RouteMatchingContext(final String path, final String method, final RequestAdapter<Q, R> adapter) {

        this.path = path;
        this.method = method;
        this.adapter = adapter;
    }

    public RouteMatchingContext<Q, R> withPath(final String newPath) {

        return new RouteMatchingContext<>(newPath, method, this.adapter);
    }

    public Optional<String> getQueryParam(String name) {

        return adapter.getQueryParam(name);
    }

    public Q getRequest() {

        return adapter.getRequest();
    }

    public R getResponse() {

        return adapter.getResponse();
    }
}
