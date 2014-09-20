package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public abstract class ActionParameter {
    public abstract String getJavaParameter(int depth);

    public abstract String getReverseParameter();

    public abstract boolean isReverseParameter();

    public abstract void toUriBuilder(final IdentBuilder builder);
}
