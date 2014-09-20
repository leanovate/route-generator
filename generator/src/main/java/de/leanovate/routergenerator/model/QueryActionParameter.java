package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

import java.util.Optional;

public class QueryActionParameter extends ActionParameter {
    public final String name;

    public final String type;

    public final Optional<String> defaultValue;

    public QueryActionParameter(final String name, final String type, final Optional<String> defaultValue) {

        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getReverseParameter() {

        if ( "int".equalsIgnoreCase(type))
            return "OptionalInt " + name;
        else
        return "Optional<" + type + "> " + name;
    }

    @Override
    public boolean isReverseParameter() {

        return true;
    }

    @Override
    public void toUriBuilder(final IdentBuilder build) {

        build.writeLine(String.format(".query(\"%s\", %s)", name, name));
    }

    @Override
    public String getJavaParameter(final int depth) {

        return defaultValue
                .map((value) ->
                        String.format("%sQuery(ctx%d, \"%s\").orElse(%s)", type.toLowerCase(), depth, name, value))
                .orElseGet(() ->
                        String.format("%sQuery(ctx%d, \"%s\")", type.toLowerCase(), depth, name));

    }
}
