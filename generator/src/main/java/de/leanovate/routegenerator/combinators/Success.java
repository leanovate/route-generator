package de.leanovate.routegenerator.combinators;

import java.util.function.Function;

public class Success<I extends Input, T> implements ParseResult<I, T> {
    private final T result;

    private final I next;

    public Success(final T result, final I next) {

        this.result = result;
        this.next = next;
    }

    @Override
    public T get() {

        return result;
    }

    @Override
    public boolean isSuccessful() {

        return true;
    }

    @Override
    public boolean isError() {

        return false;
    }

    @Override
    public I getNext() {

        return next;
    }

    @Override
    public <U> ParseResult<I, U> mapNoSuccess() {

        throw new RuntimeException("Parse result was successful");
    }

    @Override
    public <U> ParseResult<I, U> map(final Function<T, U> f) {

        return new Success<>(f.apply(result), next);
    }

    @Override
    public <U> ParseResult<I, U> flatMapWithNext(final Function<T, ? extends Function<I, ParseResult<I, U>>> f) {

        return f.apply(result).apply(next);
    }
}
