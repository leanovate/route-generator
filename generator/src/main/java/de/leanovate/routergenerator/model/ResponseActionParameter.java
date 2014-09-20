package de.leanovate.routergenerator.model;

import de.leanovate.routergenerator.builder.IdentBuilder;

public class ResponseActionParameter extends ActionParameter {
    @Override
    public String getJavaParameter(final int depth) {

        return String.format("ctx%d.getResponse()", depth);
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
