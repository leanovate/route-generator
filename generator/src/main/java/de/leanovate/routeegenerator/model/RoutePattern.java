package de.leanovate.routeegenerator.model;

import de.leanovate.routeegenerator.builder.IdentBuilder;

public abstract class RoutePattern {
    public abstract void build(IdentBuilder builder, String delim, int depth);
}
