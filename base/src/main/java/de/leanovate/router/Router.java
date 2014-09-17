package de.leanovate.router;

public interface Router<Q, R> {
    boolean route(RouteMatchingContext<Q, R> context);
}