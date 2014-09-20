package de.leanovate.routergenerator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRule {
    public final MethodRoutePattern methodRoutePattern;

    public final List<PathRoutePattern> pathRoutePatterns;

    public RouteRule(final List<PathRoutePattern> pathRoutePatterns, final MethodRoutePattern methodRoutePattern) {

        this.methodRoutePattern = methodRoutePattern;
        this.pathRoutePatterns = pathRoutePatterns;
    }

    public void check() {

        final Map<String, PathRoutePattern> patternsByName = new HashMap<>();
        for (final PathRoutePattern pathRoutePattern : pathRoutePatterns) {
            if (pathRoutePattern instanceof SegementRoutePattern) {
                if (patternsByName.containsKey(((SegementRoutePattern) pathRoutePattern).name)) {
                    throw new RuntimeException(
                            "path paremter " + ((SegementRoutePattern) pathRoutePattern).name + " already defined");
                }
                patternsByName.put(((SegementRoutePattern) pathRoutePattern).name, pathRoutePattern);
            } else if (pathRoutePattern instanceof RemainingRoutePattern) {
                if (patternsByName.containsKey(((RemainingRoutePattern) pathRoutePattern).name)) {
                    throw new RuntimeException(
                            "path paremter " + ((RemainingRoutePattern) pathRoutePattern).name + " already defined");
                }
                patternsByName.put(((RemainingRoutePattern) pathRoutePattern).name, pathRoutePattern);
            }
        }

        for (ActionParameter parameter : this.methodRoutePattern.controllerAction.parameters) {
            if (parameter instanceof PathActionParameter) {
                final PathActionParameter pathActionParameter = (PathActionParameter) parameter;
                final PathRoutePattern pathRoutePattern = patternsByName.get(pathActionParameter.name);

                if (pathRoutePattern == null) {
                    throw new RuntimeException("parameter " + pathActionParameter.name + " not defined");
                }
                if (pathRoutePattern instanceof SegementRoutePattern) {
                    ((SegementRoutePattern) pathRoutePattern).type = pathActionParameter.type;
                } else if (pathRoutePattern instanceof RemainingRoutePattern) {
                    if (!"string".equalsIgnoreCase(pathActionParameter.type)) {
                        throw new RuntimeException(
                                "parameter " + pathActionParameter.name + " has to be of type string");
                    }
                }
            }
        }
    }
}
