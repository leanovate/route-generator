package de.leanovate.routergenerator.combinators;

import java.util.function.Function;

public interface ParseResult<I extends Input, T> {
    T get();

    boolean isSuccessful();

    boolean isError();

    I getNext();

    <U> ParseResult<I, U> mapNoSuccess();

    <U> ParseResult<I, U> map(Function<T, U> f);

    <U> ParseResult<I, U> flatMapWithNext(final Function<T, ? extends Function<I, ParseResult<I, U>>> f);
}
