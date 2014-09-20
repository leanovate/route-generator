package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public class ValueActionParameter extends ActionParameter {
    public final String value;

    public ValueActionParameter(final String value) {

        this.value = value;
    }

    @Override
    public String getReverseParameter() {

        return "";
    }

    @Override
    public String getJavaParameter(final int depth) {

        return value;
    }

    @Override
    public boolean isReverseParameter() {

        return false;
    }

    @Override
    public void toUriBuilder(final IdentBuilder builder) {

    }
}
