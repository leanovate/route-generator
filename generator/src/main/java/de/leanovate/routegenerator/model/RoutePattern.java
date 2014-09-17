package de.leanovate.routegenerator.model;

import de.leanovate.routegenerator.builder.IdentBuilder;

public abstract class RoutePattern {
    public abstract void build(IdentBuilder builder, String delim, int depth);
}
