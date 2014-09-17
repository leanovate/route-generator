package de.leanovate.routergenerator.model;

public class RemainingRoutePattern extends PathRoutePattern {
    public final String name;

    public RemainingRoutePattern(final String name) {

        this.name = name;
    }

    @Override
    protected String getJavaRule(int depth) {

        return String.format("remaining(ctx%d, (%s, ctx%d) ->", depth, name, depth + 1);
    }
}
