package de.leanovate.routergenerator.model;

import java.util.Objects;

public class SegementRoutePattern extends PathRoutePattern {
    public final String name;

    public String type = "string";

    public SegementRoutePattern(final String name) {

        this.name = name;
    }

    @Override
    protected String getJavaRule(int depth) {

        return String.format("%sSegment(ctx%d, (ctx%d, %s) ->", type, depth, depth + 1, name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SegementRoutePattern other = (SegementRoutePattern) obj;
        return Objects.equals(this.name, other.name);
    }
}
