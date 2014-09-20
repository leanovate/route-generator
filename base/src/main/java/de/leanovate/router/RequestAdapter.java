package de.leanovate.router;

import java.util.Optional;

/**
 * Log level request/response adapter of the underlying framework.
 *
 * @param <Q> the http request of the underlying framework
 * @param <R> the http response of the underlying framework
 */
public interface RequestAdapter<Q, R> {
    /**
     * The the path-part of the http request.
     *
     * @return the request path
     */
    String getPath();

    /**
     * The the http method of the http request.
     *
     * @return the http request method
     */
    String getMethod();

    /**
     * Get an optional query parameter.
     *
     * @param name the name of the query parameter
     * @return optional value
     */
    Optional<String> getQueryParam(String name);

    /**
     * Get the the http request object of the underlying framework.
     *
     * @return the http request of the underlying framework
     */
    Q getRequest();

    /**
     * Get the the http response object of the underlying framework.
     *
     * @return the http response of the underlying framework
     */
    R getResponse();

    /**
     * Create a matching context of this adapted request.
     *
     * @return request context for matching
     */
    default RouteMatchingContext<Q, R> createContext() {

        return new RouteMatchingContext<>(getPath(), getMethod(), this);
    }
}
