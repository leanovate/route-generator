package de.leanovate.routegenerator.combinators;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CoreParsers {
    public static <I extends Input, V1, V2> Parser<I, Tuples.T2<V1, V2>> sequence(
            final Parser<I, V1> p1,
            final Parser<I, V2> p2) {

        return (in) -> {
            final ParseResult<I, V1> r1 = p1.apply(in);
            if (!r1.isSuccessful()) {
                return r1.mapNoSuccess();
            }

            final ParseResult<I, V2> r2 = p2.apply(r1.getNext());
            if (!r2.isSuccessful()) {
                return r2.mapNoSuccess();
            }

            return new Success<>(new Tuples.T2<>(r1.get(), r2.get()), r2.getNext());
        };
    }

    public static <I extends Input, V1, V2, V3> Parser<I, Tuples.T3<V1, V2, V3>> sequence(
            final Parser<I, V1> p1,
            final Parser<I, V2> p2,
            final Parser<I, V3> p3) {

        return (in) -> {
            final ParseResult<I, V1> r1 = p1.apply(in);
            if (!r1.isSuccessful()) {
                return r1.mapNoSuccess();
            }

            final ParseResult<I, V2> r2 = p2.apply(r1.getNext());
            if (!r2.isSuccessful()) {
                return r2.mapNoSuccess();
            }

            final ParseResult<I, V3> r3 = p3.apply(r2.getNext());
            if (!r3.isSuccessful()) {
                return r3.mapNoSuccess();
            }

            return new Success<>(new Tuples.T3<>(r1.get(), r2.get(), r3.get()), r3.getNext());
        };
    }

    public static <I extends Input, V1, V2, V3, V4> Parser<I, Tuples.T4<V1, V2, V3, V4>> sequence(
            final Parser<I, V1> p1,
            final Parser<I, V2> p2,
            final Parser<I, V3> p3,
            final Parser<I, V4> p4) {

        return (in) -> {
            final ParseResult<I, V1> r1 = p1.apply(in);
            if (!r1.isSuccessful()) {
                return r1.mapNoSuccess();
            }

            final ParseResult<I, V2> r2 = p2.apply(r1.getNext());
            if (!r2.isSuccessful()) {
                return r2.mapNoSuccess();
            }

            final ParseResult<I, V3> r3 = p3.apply(r2.getNext());
            if (!r3.isSuccessful()) {
                return r3.mapNoSuccess();
            }

            final ParseResult<I, V4> r4 = p4.apply(r3.getNext());
            if (!r4.isSuccessful()) {
                return r4.mapNoSuccess();
            }

            return new Success<>(new Tuples.T4<>(r1.get(), r2.get(), r3.get(), r4.get()), r4.getNext());
        };
    }

    public static <I extends Input, V1, V2, V3, V4, V5> Parser<I, Tuples.T5<V1, V2, V3, V4, V5>> sequence(
            final Parser<I, V1> p1,
            final Parser<I, V2> p2,
            final Parser<I, V3> p3,
            final Parser<I, V4> p4,
            final Parser<I, V5> p5) {

        return (in) -> {
            final ParseResult<I, V1> r1 = p1.apply(in);
            if (!r1.isSuccessful()) {
                return r1.mapNoSuccess();
            }

            final ParseResult<I, V2> r2 = p2.apply(r1.getNext());
            if (!r2.isSuccessful()) {
                return r2.mapNoSuccess();
            }

            final ParseResult<I, V3> r3 = p3.apply(r2.getNext());
            if (!r3.isSuccessful()) {
                return r3.mapNoSuccess();
            }

            final ParseResult<I, V4> r4 = p4.apply(r3.getNext());
            if (!r4.isSuccessful()) {
                return r4.mapNoSuccess();
            }

            final ParseResult<I, V5> r5 = p5.apply(r4.getNext());
            if (!r5.isSuccessful()) {
                return r5.mapNoSuccess();
            }

            return new Success<>(new Tuples.T5<>(r1.get(), r2.get(), r3.get(), r4.get(), r5.get()), r5.getNext());
        };
    }

    public static <I extends Input, V1, V2, V3, V4, V5, V6> Parser<I, Tuples.T6<V1, V2, V3, V4, V5, V6>> sequence(
            final Parser<I, V1> p1,
            final Parser<I, V2> p2,
            final Parser<I, V3> p3,
            final Parser<I, V4> p4,
            final Parser<I, V5> p5,
            final Parser<I, V6> p6) {

        return (in) -> {
            final ParseResult<I, V1> r1 = p1.apply(in);
            if (!r1.isSuccessful()) {
                return r1.mapNoSuccess();
            }

            final ParseResult<I, V2> r2 = p2.apply(r1.getNext());
            if (!r2.isSuccessful()) {
                return r2.mapNoSuccess();
            }

            final ParseResult<I, V3> r3 = p3.apply(r2.getNext());
            if (!r3.isSuccessful()) {
                return r3.mapNoSuccess();
            }

            final ParseResult<I, V4> r4 = p4.apply(r3.getNext());
            if (!r4.isSuccessful()) {
                return r4.mapNoSuccess();
            }

            final ParseResult<I, V5> r5 = p5.apply(r4.getNext());
            if (!r5.isSuccessful()) {
                return r5.mapNoSuccess();
            }

            final ParseResult<I, V6> r6 = p6.apply(r5.getNext());
            if (!r6.isSuccessful()) {
                return r6.mapNoSuccess();
            }

            return new Success<>(new Tuples.T6<>(r1.get(), r2.get(), r3.get(), r4.get(), r5.get(), r6.get()),
                    r6.getNext());
        };
    }

    @SafeVarargs
    public static <I extends Input, T> Parser<I, T> firstOf(Parser<I, T> first,
            Parser<I, T>... parsers) {

        return (in) -> {
            ParseResult<I, T> firstResult = first.apply(in);

            if (!firstResult.isSuccessful() && !firstResult.isError()) {
                for (final Parser<I, T> parser : parsers) {
                    final ParseResult<I, T> result = parser.apply(in);

                    if (result.isSuccessful() || result.isError()) {
                        return result;
                    }
                }
            }
            return firstResult;
        };
    }

    public static <I extends Input, T> Parser<I, Optional<T>> opt(Parser<I, T> parser) {

        return (in) -> {
            ParseResult<I, T> result = parser.apply(in);

            if (result.isSuccessful()) {
                return new Success<>(Optional.of(result.get()), result.getNext());
            } else if (!result.isError()) {
                return new Success<>(Optional.<T>empty(), result.getNext());
            } else {
                return result.mapNoSuccess();
            }
        };
    }

    public static <I extends Input, T> Parser<I, List<T>> zeroOrMore(final Parser<I, T> parser) {

        return (in) -> {
            final List<T> results = new ArrayList<>();
            I current = in;
            ParseResult<I, T> result = parser.apply(current);

            while (result.isSuccessful()) {
                current = result.getNext();
                results.add(result.get());
                result = parser.apply(current);
            }
            if (result.isError()) {
                return result.mapNoSuccess();
            }

            return new Success<>(results, current);
        };
    }

    public static <I extends Input, T> Parser<I, List<T>> zeroOrMoreSep(final Parser<I, T> element,
            final Parser<I, ?> separator) {

        return (in) -> {
            final List<T> results = new ArrayList<>();
            I current = in;
            ParseResult<I, T> result = element.apply(current);

            while (result.isSuccessful()) {
                current = result.getNext();
                results.add(result.get());
                final ParseResult<I, ?> separatorResult = separator.apply(current);

                if (separatorResult.isError()) {
                    return separatorResult.mapNoSuccess();
                } else if (!separatorResult.isSuccessful()) {
                    break;
                } else {
                    current = separatorResult.getNext();
                }

                result = element.apply(current);
            }
            if (result.isError()) {
                return result.mapNoSuccess();
            }

            return new Success<>(results, current);
        };
    }

    public static <I extends Input, T> Parser<I, List<T>> oneOrMore(final Parser<I, T> parser) {

        return (in) -> {
            final List<T> results = new ArrayList<>();
            I current = in;
            ParseResult<I, T> result = parser.apply(current);

            if (!result.isSuccessful()) {
                return result.mapNoSuccess();
            } else {
                while (result.isSuccessful()) {
                    current = result.getNext();
                    results.add(result.get());
                    result = parser.apply(current);
                }
            }
            if (result.isError()) {
                return result.mapNoSuccess();
            }

            return new Success<>(results, current);
        };
    }

    public static <I extends Input, T> Parser<I, List<T>> oneOrMoreSep(final Parser<I, T> element,
            final Parser<I, ?> separator) {

        return (in) -> {
            final List<T> results = new ArrayList<>();
            I current = in;
            ParseResult<I, T> result = element.apply(current);

            if (!result.isSuccessful()) {
                return result.mapNoSuccess();
            } else {
                while (result.isSuccessful()) {
                    current = result.getNext();
                    results.add(result.get());
                    final ParseResult<I, ?> separatorResult = separator.apply(current);

                    if (separatorResult.isError()) {
                        return separatorResult.mapNoSuccess();
                    } else if (!separatorResult.isSuccessful()) {
                        break;
                    } else {
                        current = separatorResult.getNext();
                    }
                    result = element.apply(current);
                }
            }
            if (result.isError()) {
                return result.mapNoSuccess();
            }

            return new Success<>(results, current);
        };
    }

    public static <I extends Input, T> Parser<I, T> suppliedBy(final Supplier<Parser<I, T>> parserSupplier) {

        return (in) -> parserSupplier.get().apply(in);
    }

    public static <I extends Input, T> Parser<I, T> success(final T result) {

        return (in) -> new Success<>(result, in);
    }

    public static <I extends Input, T> Parser<I, T> failure(String message) {

        return (in) -> new Failure<>(message, in);
    }

    public static <I extends Input> Parser<I, Void> eof() {

        return (in) -> {
            if (in.isAtEnd()) {
                return new Success<>(null, in);
            }
            return new Failure<>("Parse error", in);
        };
    }
}
