package de.leanovate.router;

public class RouteMatchingContext<Q,R> {
    public final String path;

    public final String method;

    public final RequestAdapter<Q,R> request;

    public RouteMatchingContext(final String path, final String method, final RequestAdapter<Q,R> request) {

        this.path = path;
        this.method = method;
        this.request = request;
    }

    public RouteMatchingContext<Q,R> withPath(final String newPath) {

        return new RouteMatchingContext<>(newPath, method, this.request);
    }
}
