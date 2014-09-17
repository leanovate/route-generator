package de.leanovate.routegenerator;

import de.leanovate.routegenerator.builder.IdentBuilder;
import de.leanovate.routegenerator.builder.JavaClassBuilder;
import de.leanovate.routegenerator.builder.JavaFileBuilder;
import de.leanovate.routegenerator.model.EndRoutePattern;
import de.leanovate.routegenerator.model.PathRoutePattern;
import de.leanovate.routegenerator.model.RemainingRoutePattern;
import de.leanovate.routegenerator.model.RoutePattern;
import de.leanovate.routegenerator.model.RouteRule;

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
        } else if (!(last instanceof RemainingRoutePattern)) {
            last = getOrAdd(last.children, new EndRoutePattern());
        }

        getOrAdd(last.children, routeRule.methodRoutePattern);
        if (routeRule.methodRoutePattern.controllerAction.dynamic) {
            dynamicControllers.add(routeRule.methodRoutePattern.controllerAction.clazz);
        }
    }

    public void build(final JavaFileBuilder javaFileBuilder) {

        javaFileBuilder.addImport("de.leanovate.router.RouteMatchingContext");
        javaFileBuilder.addImport("static de.leanovate.router.CommonRouteRules.*");

        if (dynamicControllers.isEmpty()) {
            javaFileBuilder.publicClass(this::generateRouteMethod);
        } else {
            javaFileBuilder.publicAbstractClass((classBuilder) -> {
                generateRouteMethod(classBuilder);
                dynamicControllers.forEach((dynamicController) -> classBuilder
                        .protectedAbstractMethod(dynamicController, "get" + dynamicController, new String[0]));
            });
        }
    }

    private void generateRouteMethod(final JavaClassBuilder classBuilder) {

        classBuilder.publicMethod("boolean", "route", new String[] { "final RouteMatchingContext ctx0" },
                (methodBuilder) -> {
                    methodBuilder.writeLine("return");
                    generateRules(methodBuilder);
                    methodBuilder.writeLine(";");
                });
    }

    private void generateRules(final IdentBuilder builder) {

        if (rootPatterns.isEmpty()) {
            builder.writeLine("false");
        } else {
            String delim = "";

            for (RoutePattern routePattern : rootPatterns) {
                routePattern.build(builder, delim, 0);
                delim = "|| ";
            }
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
