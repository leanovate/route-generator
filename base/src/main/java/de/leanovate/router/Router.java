package de.leanovate.router;

/**
 * Common interface of all generated routes.
 *
 * @param <Q> the http request of the underlying framework
 * @param <R> the http response of the underlying framework
 */
public interface Router<Q, R> {
    /**
     * Try to route a request.
     * 
     * Note: If no matching rout is found, this method does not send any kind of response. I.e. you have to send the
     * {@code 404} by yourself.
     *
     * @param context request context adapting the underlying framework
     * @return {@code true} if the http request was successfully routed, {@code false} of no matching route was found.
     */
    boolean route(RouteMatchingContext<Q, R> context);
}