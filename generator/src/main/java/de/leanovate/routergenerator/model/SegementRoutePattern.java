package de.leanovate.routergenerator.model;

import java.util.Objects;

public class SegementRoutePattern extends PathRoutePattern {
    public final String name;

    public SegementRoutePattern(final String name) {

        this.name = name;
    }

    @Override
    protected String getJavaRule(int depth) {

        return String.format("stringSegment(ctx%d, (%s, ctx%d) ->", depth, name, depth + 1);
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