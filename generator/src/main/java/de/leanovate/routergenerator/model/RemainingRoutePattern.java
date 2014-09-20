package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public class RemainingRoutePattern extends PathRoutePattern {
    public final String name;

    public RemainingRoutePattern(final String name) {

        this.name = name;
    }

    @Override
    protected String getJavaRule(int depth) {

        return String.format("remaining(ctx%d, (ctx%d, %s) ->", depth, depth + 1, name);
    }

    @Override
    public String toUriTemplate() {

        return String.format("{/%s*}", name);
    }

    @Override
    public void toUriBuilder(final IdentBuilder builder) {

        builder.writeLine(String.format(".path(%s)", name));
    }
}
