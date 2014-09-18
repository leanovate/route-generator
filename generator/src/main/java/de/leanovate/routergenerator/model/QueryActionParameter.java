package de.leanovate.routergenerator.model;

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
    public String getJavaParameter(final int depth) {

        return defaultValue
                .map((value) ->
                        String.format("%sQuery(ctx%d, \"%s\").orElse(%s)", type, depth, name, value))
                .orElseGet(() ->
                        String.format("%sQuery(ctx%d, \"%s\")", type, depth, name));

    }
}
