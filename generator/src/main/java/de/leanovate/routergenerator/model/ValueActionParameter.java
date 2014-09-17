package de.leanovate.routergenerator.model;

public class ValueActionParameter extends ActionParameter {
    public final String value;

    public ValueActionParameter(final String value) {

        this.value = value;
    }

    @Override
    public String getJavaParameter(final int depth) {

        return value;
    }
}
