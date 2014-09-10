package de.leanovate.routeegenerator.combinators;

public class Error<I extends Input, T> extends NoSuccess<I, T> {
    protected Error(final String message, final I next) {

        super(message, next);
    }

    @Override
    public boolean isError() {

        return true;
    }
}
