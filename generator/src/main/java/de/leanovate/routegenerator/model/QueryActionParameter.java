package de.leanovate.routegenerator.model;

import java.util.Optional;

public class QueryActionParameter extends ActionParameter {
    public final String name;

    public final Optional<String> defaultValue;

    public QueryActionParameter(final String name, final Optional<String> defaultValue) {

        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getJavaParameter(final int depth) {

        return defaultValue
                .map((value) ->
                        String.format("ctx%d.request.getQueryParam(\"%s\").orElse(%s)", depth, name, value))
                .orElseGet(() ->
                        String.format("ctx%d.request.getQueryParam(\"%s\")", depth, name));

    }
}
