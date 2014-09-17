package de.leanovate.routegenerator.combinators;

import java.util.function.Function;

@FunctionalInterface
public interface Parser<I extends Input, T> extends Function<I, ParseResult<I, T>> {
    default <U> Parser<I, U> flatMap(Function<T, Parser<I, U>> f) {

        return (in) -> this.apply(in).flatMapWithNext(f);
    }

    default <U> Parser<I, U> map(Function<T, U> f) {

        return (in) -> this.apply(in).map(f);
    }
}
