package de.leanovate.routeegenerator.combinators;

public class Failure<I extends Input, T> extends NoSuccess<I, T> {

    public Failure(final String message, final I next) {

        super(message, next);
    }

    @Override
    public boolean isError() {

        return false;
    }
}
