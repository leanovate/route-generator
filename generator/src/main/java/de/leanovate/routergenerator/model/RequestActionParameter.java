package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public class RequestActionParameter extends ActionParameter {
    @Override
    public String getJavaParameter(final int depth) {

        return String.format("ctx%d.getRequest()", depth);
    }

    @Override
    public String getReverseParameter() {

        return "";
    }

    @Override
    public boolean isReverseParameter() {

        return false;
    }

    @Override
    public void toUriBuilder(final IdentBuilder builder) {

    }
}
