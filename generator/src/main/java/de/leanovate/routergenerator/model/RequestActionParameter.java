package de.leanovate.routergenerator.model;

public class RequestActionParameter extends ActionParameter {
    @Override
    public String getJavaParameter(final int depth) {

        return String.format("ctx%d.getRequest()", depth);
    }
}
