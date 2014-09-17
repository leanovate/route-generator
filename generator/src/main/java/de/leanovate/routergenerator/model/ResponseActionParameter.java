package de.leanovate.routergenerator.model;

public class ResponseActionParameter extends ActionParameter {
    @Override
    public String getJavaParameter(final int depth) {

        return String.format("ctx%d.getResponse()", depth);
    }
}
