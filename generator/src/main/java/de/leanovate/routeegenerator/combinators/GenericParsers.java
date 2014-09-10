package de.leanovate.routeegenerator.combinators;

import java.util.function.Function;

public interface GenericParsers<E> {
    default Parser<GenericInput<E>, E> elem(final String kind, final Function<E, Boolean> condition) {

        return acceptIf(condition, (in) -> kind + " expected");
    }

    default Parser<GenericInput<E>, E> elem(final E element) {

        return acceptIf(element::equals, (in) -> "'" + element + "' expected but " + in + " found");
    }

    default Parser<GenericInput<E>, E> acceptIf(final Function<E, Boolean> condition, final Function<E, String> error) {

        return (in) -> {
            if (in.isAtEnd()) {
                return new Failure<>("end of input", in);
            } else if (condition.apply(in.getFirst())) {
                return new Success<>(in.getFirst(), in.getRest());
            } else {
                return new Failure<>(error.apply(in.getFirst()), in);
            }
        };
    }
}
