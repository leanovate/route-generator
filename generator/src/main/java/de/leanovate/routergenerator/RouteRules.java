package de.leanovate.routergenerator;

import de.leanovate.routergenerator.builder.IdentBuilder;
import de.leanovate.routergenerator.builder.JavaClassBuilder;
import de.leanovate.routergenerator.builder.JavaFileBuilder;
import de.leanovate.routergenerator.model.ActionParameter;
import de.leanovate.routergenerator.model.ControllerAction;
import de.leanovate.routergenerator.model.EndRoutePattern;
import de.leanovate.routergenerator.model.PathRoutePattern;
import de.leanovate.routergenerator.model.RemainingRoutePattern;
import de.leanovate.routergenerator.model.RoutePattern;
import de.leanovate.routergenerator.model.RouteRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RouteRules {

    List<RoutePattern> rootPatterns = new ArrayList<>();

    Map<ControllerAction, List<PathRoutePattern>> patternsByAction = new HashMap<>();

    public String packageName = "";

    public String requestClass = "javax.servlet.http.HttpServletRequest";

    public String responseClass = "javax.servlet.http.HttpServletResponse";

    Set<String> dynamicControllers = new TreeSet<>();

    public void addRule(final RouteRule routeRule) {

        routeRule.check();

        if (!patternsByAction.containsKey(routeRule.methodRoutePattern.controllerAction)) {
            patternsByAction.put(routeRule.methodRoutePattern.controllerAction, routeRule.pathRoutePatterns);
        }

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

    public void buildRouter(final JavaFileBuilder javaFileBuilder) {

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
                        .protectedAbstractMethod(dynamicController, "get" + dynamicController, Arrays.asList()));
            });
        }
    }

    public void buildReverseRoutes(final JavaFileBuilder javaFileBuilder) {

        javaFileBuilder.addImport("de.leanovate.router.UriBuilder");
        javaFileBuilder.addImport("java.util.*");

        javaFileBuilder.publicClass("", this::generateReverseRoutes);
    }

    private void generateRouteMethod(final JavaClassBuilder classBuilder) {

        String parameter = String.format("final RouteMatchingContext<%s, %s> ctx0", requestClass, responseClass);
        classBuilder.publicMethod("boolean", "route", Arrays.asList(parameter),
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

    private void generateReverseRoutes(final JavaClassBuilder classBuilder) {

        ControllerPackage root = new ControllerPackage();

        for (Map.Entry<ControllerAction, List<PathRoutePattern>> entry : patternsByAction.entrySet()) {
            ControllerPackage current = root;

            for (String part : entry.getKey().clazz.split("\\.")) {
                ControllerNode next = current.children.get(part);

                if (next == null) {
                    next = new ControllerPackage();
                    current.children.put(part, next);
                } else if (!(next instanceof ControllerPackage)) {
                    break;
                }
                current = (ControllerPackage) next;
            }
            if (!current.children.containsKey(entry.getKey().method)) {
                current.children.put(entry.getKey().method, new ControllerMethod(entry.getKey(), entry.getValue()));
            }
        }

        root.buildReverseRoutes(classBuilder);
    }

    static interface ControllerNode {
        void buildReverseRoutes(final JavaClassBuilder classBuilder);
    }

    static class ControllerPackage implements ControllerNode {
        Map<String, ControllerNode> children = new TreeMap<>();

        @Override
        public void buildReverseRoutes(final JavaClassBuilder classBuilder) {

            for (Map.Entry<String, ControllerNode> entry : children.entrySet()) {
                if (entry.getValue() instanceof ControllerPackage) {
                    classBuilder.publicStaticInnerClass(entry.getKey(), entry.getValue()::buildReverseRoutes);
                } else {
                    entry.getValue().buildReverseRoutes(classBuilder);
                }
            }
        }
    }

    static class ControllerMethod implements ControllerNode {

        final ControllerAction controllerAction;

        final List<PathRoutePattern> pathRoutePatterns;

        ControllerMethod(final ControllerAction controllerAction, final List<PathRoutePattern> pathRoutePatterns) {

            this.controllerAction = controllerAction;
            this.pathRoutePatterns = pathRoutePatterns;
        }

        @Override
        public void buildReverseRoutes(final JavaClassBuilder classBuilder) {

            classBuilder.publicConstant(controllerAction.method, pathRoutePatterns.stream().map(
                    PathRoutePattern::toUriTemplate).collect(Collectors.joining()));

            List<String> parameters = controllerAction.parameters.stream().filter(ActionParameter::isReverseParameter)
                    .map(ActionParameter::getReverseParameter)
                    .collect(Collectors.toList());
            classBuilder.publicMethod("String", controllerAction.method, parameters, (body) -> {
                body.writeLine("return new UriBuilder()");
                body.ident((uriParts) -> {
                    pathRoutePatterns.forEach((pathRoutePattern) -> pathRoutePattern.toUriBuilder(uriParts));
                    controllerAction.parameters.forEach((parameter) -> parameter.toUriBuilder(uriParts));
                    uriParts.writeLine(".build();");
                });
            });
        }
    }
}
