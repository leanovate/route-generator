package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public abstract class RoutePattern {
    public abstract void build(IdentBuilder builder, String delim, int depth);
}
