package de.leanovate.routergenerator;

import de.leanovate.routergenerator.builder.IdentBuilder;
import de.leanovate.routergenerator.builder.JavaClassBuilder;
import de.leanovate.routergenerator.builder.JavaFileBuilder;
import de.leanovate.routergenerator.model.EndRoutePattern;
import de.leanovate.routergenerator.model.PathRoutePattern;
import de.leanovate.routergenerator.model.RemainingRoutePattern;
import de.leanovate.routergenerator.model.RoutePattern;
import de.leanovate.routergenerator.model.RouteRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RouteRules {

    List<RoutePattern> rootPatterns = new ArrayList<>();

    public String packageName = "";

    public String requestClass = "javax.servlet.http.HttpServletRequest";

    public String responseClass = "javax.servlet.http.HttpServletResponse";

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
        javaFileBuilder.addImport("de.leanovate.router.Router");
        javaFileBuilder.addImport("static de.leanovate.router.CommonRouteRules.*");

        String extensions = String.format(" implements Router<%s, %s>", requestClass, responseClass);
        if (dynamicControllers.isEmpty()) {
            javaFileBuilder.publicClass(extensions, this::generateRouteMethod);
        } else {
            javaFileBuilder.publicAbstractClass(extensions, (classBuilder) -> {
                generateRouteMethod(classBuilder);
                dynamicControllers.forEach((dynamicController) -> classBuilder
                        .protectedAbstractMethod(dynamicController, "get" + dynamicController, new String[0]));
            });
        }
    }

    private void generateRouteMethod(final JavaClassBuilder classBuilder) {

        String parameter = String.format("final RouteMatchingContext<%s, %s> ctx0", requestClass, responseClass);
        classBuilder.publicMethod("boolean", "route", new String[] { parameter },
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
