package de.leanovate.router;

public class RouteMatchingContext<T> {
    public final String path;

    public final String method;

    public final RequestAdapter<T> request;

    public RouteMatchingContext(final String path, final String method, final RequestAdapter<T> request) {

        this.path = path;
        this.method = method;
        this.request = request;
    }

    public RouteMatchingContext<T> withPath(final String newPath) {

        return new RouteMatchingContext<>(newPath, method, this.request);
    }
}
