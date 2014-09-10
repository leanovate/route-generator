package de.leanovate.routeegenerator.model;

import java.util.List;

public class ControllerAction {
    public final String clazz;
    public final String method;
    public final boolean dynamic;
    public final List<ActionParameter> parameters;

    public ControllerAction(final String clazz, final String method, final boolean dynamic,
            final List<ActionParameter> parameters) {

        this.clazz = clazz;
        this.method = method;
        this.dynamic = dynamic;
        this.parameters = parameters;
    }
}
