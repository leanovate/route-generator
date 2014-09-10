package de.leanovate.routeegenerator.combinators;

public class CharParsers {
    public static Parser<CharInput, Character> elem(final String kind, final CharPredicate condition) {

        return acceptIf(condition, (ch) -> kind + " expected");
    }

    public static Parser<CharInput, Character> elem(final Character element) {

        return acceptIf(element::equals, (ch) -> "'" + element + "' expected but " + ch + " found");
    }

    public static Parser<CharInput, Character> acceptIf(final CharPredicate condition,
            final CharFunction<String> error) {

        return (in) -> {
            if (in.isAtEnd()) {
                return new Failure<>("end of input", in);
            } else if (condition.test(in.getFirst())) {
                return new Success<>(in.getFirst(), in.getRest());
            } else {
                return new Failure<>(error.apply(in.getFirst()), in);
            }
        };
    }

    public static Parser<CharInput, String> keyword(final CharSequence keyword) {

        if (keyword.length() == 0) {
            return CoreParsers.success("");
        }
        return (in) -> {
            CharInput current = in;

            for (int i = 0; i < keyword.length(); i++) {
                if (current.isAtEnd() || current.getFirst() != keyword.charAt(i)) {
                    return new Failure<>(
                            keyword.charAt(i) + " expected but " + current.getFirst() + " found",
                            current);
                }
                current = current.getRest();
            }
            return new Success<>(keyword.toString(), current);
        };
    }

    public static Parser<CharInput, String> ignoreCase(final CharSequence keyword) {

        if (keyword.length() == 0) {
            return CoreParsers.success("");
        }

        return (in) -> {
            CharInput current = in;

            for (int i = 0; i < keyword.length(); i++) {
                if (current.isAtEnd() ||
                        Character.toLowerCase(current.getFirst()) != Character.toLowerCase(keyword.charAt(i))) {
                    return new Failure<>(
                            "case insensitive " + keyword.charAt(i) + " expected but " + current.getFirst() + " found",
                            current);
                }
                current = current.getRest();
            }
            return new Success<>(keyword.toString(), current);
        };
    }

    public static Parser<CharInput, String> oneOrMoreOf(final CharPredicate condition,
            final CharFunction<String> error) {

        return (in) -> {
            final StringBuilder result = new StringBuilder();

            if (in.isAtEnd()) {
                return new Failure<>("end of input", in);
            } else if (!condition.test(in.getFirst())) {
                return new Failure<>(error.apply(in.getFirst()), in);
            }
            result.append(in.getFirst());

            CharInput current = in.getRest();
            while (!current.isAtEnd() && condition.test(current.getFirst())) {
                result.append(current.getFirst());
                current = current.getRest();
            }
            return new Success<>(result.toString(), current);
        };
    }

    public static Parser<CharInput, String> oneOrMoreOf(final String candidates) {

        return oneOrMoreOf((ch) -> candidates.indexOf(ch) >= 0,
                (ch) -> "expected at least one of '" + candidates + "'  but " + ch + " found");
    }

    public static Parser<CharInput, String> zeroOrMoreOf(final CharPredicate condition) {

        return (in) -> {
            final StringBuilder result = new StringBuilder();
            CharInput current = in;

            while (!current.isAtEnd() && condition.test(current.getFirst())) {
                result.append(current.getFirst());
                current = current.getRest();
            }
            return new Success<>(result.toString(), current);
        };
    }

    public static Parser<CharInput, String> zeroOrMoreOf(final String candidates) {

        return zeroOrMoreOf((ch) -> candidates.indexOf(ch) >= 0);
    }

    public static Parser<CharInput, Character> anyOf(final String candidates) {

        return acceptIf((ch) -> candidates.indexOf(ch) >= 0,
                (ch) -> "any of '" + candidates + "' expected but " + ch + " found");
    }
}
