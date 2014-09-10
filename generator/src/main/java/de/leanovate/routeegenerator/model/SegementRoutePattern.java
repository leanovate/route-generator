package de.leanovate.routeegenerator.model;

import java.util.Objects;

public class SegementRoutePattern extends PathRoutePattern {
    public final String name;

    public SegementRoutePattern(final String name) {

        this.name = name;
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
