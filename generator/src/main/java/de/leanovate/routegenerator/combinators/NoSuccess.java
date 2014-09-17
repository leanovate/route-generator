package de.leanovate.routegenerator.combinators;

import java.util.function.Function;

public abstract class NoSuccess<I extends Input, T> implements ParseResult<I, T> {
    protected final String message;

    protected final I next;

    protected NoSuccess(final String message, final I next) {

        this.message = message;
        this.next = next;
    }

    @Override
    public T get() {

        throw new RuntimeException(next.getPosition() + ": " + message);
    }

    @Override
    public boolean isSuccessful() {

        return false;
    }

    @Override
    public I getNext() {

        return next;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> ParseResult<I, U> mapNoSuccess() {

        return (ParseResult<I, U>)this;
    }

    @Override
    public <U> ParseResult<I, U> map(final Function<T, U> f) {

        return mapNoSuccess();
    }

    @Override
    public <U> ParseResult<I, U> flatMapWithNext(final Function<T, ? extends Function<I, ParseResult<I, U>>> f) {

        return mapNoSuccess();
    }
}
