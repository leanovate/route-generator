package de.leanovate.routeegenerator.model;

import de.leanovate.routeegenerator.builder.IdentBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerAction {
    public final String clazz;

    public final String method;

    public final boolean dynamic;

    public final List<ActionParameter> parameters;

    public ControllerAction(final String clazz, final String method, final boolean dynamic,
            final List<ActionParameter> parameters) {

        this.clazz = clazz;
        this.method = method;
        this.dynamic = dynamic;
        this.parameters = parameters;
    }

    public void build(IdentBuilder builder, int depth) {

        String javaParameters = parameters.stream().map((parameter) -> parameter.getJavaParameter(depth))
                .collect(Collectors.joining(", "));

        if (dynamic) {
            builder.writeLine(String.format("get%s().%s(%s);", clazz, method, javaParameters));
        } else {
            builder.writeLine(String.format("%s.%s(%s);", clazz, method, javaParameters));
        }
    }
}
