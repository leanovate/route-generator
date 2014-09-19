package de.leanovate.routergenerator.model;

import java.util.Objects;

public class PathActionParameter extends ActionParameter {
    public final String name;

    public final String type;

    public PathActionParameter(final String name, final String type) {

        this.name = name;
        this.type = type;
    }

    @Override
    public String getJavaParameter(final int depth) {

        return name;
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
        final PathActionParameter other = (PathActionParameter) obj;
        return Objects.equals(this.name, other.name);
    }
}
