package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

import java.util.List;
import java.util.Objects;
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

    @Override
    public int hashCode() {

        return Objects.hash(clazz, method, dynamic, parameters);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ControllerAction other = (ControllerAction) obj;
        return Objects.equals(this.clazz, other.clazz) && Objects.equals(this.method, other.method) && Objects
                .equals(this.dynamic, other.dynamic) && Objects.equals(this.parameters, other.parameters);
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("ControllerAction{");
        sb.append("clazz='").append(clazz).append('\'');
        sb.append(", method='").append(method).append('\'');
        sb.append(", dynamic=").append(dynamic);
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
