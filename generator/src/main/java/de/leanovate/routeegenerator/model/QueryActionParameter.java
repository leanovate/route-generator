package de.leanovate.routeegenerator.model;

import java.util.Optional;

public class QueryActionParameter extends ActionParameter {
    public final String name;

    public final Optional<String> defaultValue;

    public QueryActionParameter(final String name, final Optional<String> defaultValue) {

        this.name = name;
        this.defaultValue = defaultValue;
    }
}
