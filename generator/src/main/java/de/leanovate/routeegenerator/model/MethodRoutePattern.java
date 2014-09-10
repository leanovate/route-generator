package de.leanovate.routeegenerator.model;

import java.util.Objects;

public class MethodRoutePattern extends RoutePattern {
    public final String method;
    public final ControllerAction controllerAction;

    public MethodRoutePattern(final String method, final ControllerAction controllerAction) {

        this.method = method;
        this.controllerAction = controllerAction;
    }

    @Override
    public int hashCode() {

        return Objects.hash(method);
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MethodRoutePattern other = (MethodRoutePattern) obj;
        return Objects.equals(this.method, other.method);
    }

    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("MethodRoutePattern{");
        sb.append("method='").append(method).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
