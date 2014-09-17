package de.leanovate.routegenerator.model;

import java.util.Objects;

public class PrefixRoutePattern extends PathRoutePattern {
    public final String prefix;

    public PrefixRoutePattern(final String prefix) {

        this.prefix = prefix;
    }

    @Override
    protected String getJavaRule(int depth) {

        return String.format("prefix(ctx%d, \"%s\", (ctx%d) ->", depth, prefix, depth + 1);
    }

    @Override
    public int hashCode() {

        return Objects.hash(prefix);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PrefixRoutePattern other = (PrefixRoutePattern) obj;
        return Objects.equals(this.prefix, other.prefix);
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("PrefixRoutePattern{");
        sb.append("prefix='").append(prefix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}