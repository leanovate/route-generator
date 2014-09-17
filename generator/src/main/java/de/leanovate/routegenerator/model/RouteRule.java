package de.leanovate.routegenerator.model;

import java.util.List;

public class RouteRule {
    public final MethodRoutePattern methodRoutePattern;

    public final List<PathRoutePattern> pathRoutePatterns;

    public RouteRule(final List<PathRoutePattern> pathRoutePatterns, final MethodRoutePattern methodRoutePattern) {

        this.methodRoutePattern = methodRoutePattern;
        this.pathRoutePatterns = pathRoutePatterns;
    }
}
