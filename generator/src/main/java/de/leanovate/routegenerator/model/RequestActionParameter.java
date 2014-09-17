package de.leanovate.routegenerator.model;

public class RequestActionParameter extends ActionParameter {
    @Override
    public String getJavaParameter(final int depth) {

        return String.format("ctx%d.request", depth);
    }
}
