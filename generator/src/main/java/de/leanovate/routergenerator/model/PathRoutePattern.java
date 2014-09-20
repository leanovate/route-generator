package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class PathRoutePattern extends RoutePattern {
    public List<RoutePattern> children = new ArrayList<>();

    public PathRoutePattern() {

    }

    @Override
    public void build(final IdentBuilder builder, final String delim, final int depth) {

        builder.writeLine(String.format("%s%s", delim, getJavaRule(depth), depth));
        builder.ident((childBuilder) -> {
            if (children.isEmpty()) {
                childBuilder.writeLine("false");
            } else {
                String childDelim = "";
                for (RoutePattern child : children) {
                    child.build(childBuilder, childDelim, depth + 1);
                    childDelim = "|| ";
                }
            }
        });
        builder.writeLine(")");
    }

    protected abstract String getJavaRule(int depth);

    public abstract String toUriTemplate();

    public abstract void toUriBuilder(final IdentBuilder builder);
}
