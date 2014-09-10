package de.leanovate.routeegenerator;

import de.leanovate.routeegenerator.builder.JavaFileBuilder;
import de.leanovate.routeegenerator.model.PathRoutePattern;
import de.leanovate.routeegenerator.model.RoutePattern;
import de.leanovate.routeegenerator.model.RouteRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RouteRules {

    List<RoutePattern> rootPatterns = new ArrayList<>();

    String packageName = "";

    Set<String> dynamicControllers = new TreeSet<>();

    public void addRule(final RouteRule routeRule) {

        PathRoutePattern last = null;
        for (PathRoutePattern newPathRoutePattern : routeRule.pathRoutePatterns) {
            last = getOrAdd(last != null ? last.children : rootPatterns, newPathRoutePattern);
        }
        if (last == null) {
            throw new RuntimeException("No path pattern");
        }
        getOrAdd(last.children, routeRule.methodRoutePattern);
    }

    public void build(final JavaFileBuilder javaFileBuilder) {

        javaFileBuilder.addImport("static de.leanovate.router.CommonRouteRules.*");

        if (dynamicControllers.isEmpty()) {
            javaFileBuilder.publicClass((classBuilder) -> {

            });
        } else {
            javaFileBuilder.publicAbstractClass((classBuilder) -> {
                dynamicControllers.forEach((dynamicController) -> classBuilder
                        .protectedAbstractMethod(dynamicController, "get" + dynamicController, new String[0]));
            });
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends RoutePattern> T getOrAdd(final List<RoutePattern> patterns, final T pathRoutePattern) {

        return (T) patterns.stream().filter(pathRoutePattern::equals).findFirst().orElseGet(() -> {
            patterns.add(pathRoutePattern);
            return pathRoutePattern;
        });
    }
}
