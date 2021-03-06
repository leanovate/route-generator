package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public class EndRoutePattern extends PathRoutePattern {
    @Override
    protected String getJavaRule(int depth) {

        return String.format("end(ctx%d, (ctx%d) ->", depth, depth + 1);
    }

    @Override
    public String toUriTemplate() {

        return "";
    }

    @Override
    public void toUriBuilder(final IdentBuilder builder) {

    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return 0;
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("EndRoutePattern{");
        sb.append('}');
        return sb.toString();
    }
}
