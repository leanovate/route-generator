package de.leanovate.routegenerator.combinators;

import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class ParseResultMatchers {
    public static Matcher<ParseResult<?, ?>> wasSuccessful() {

        return new CustomTypeSafeMatcher<ParseResult<?, ?>>("parse result was successful") {
            @Override
            protected boolean matchesSafely(final ParseResult<?, ?> item) {

                return item.isSuccessful();
            }
        };
    }

    public static Matcher<ParseResult<?, ?>> hasNoMoreInput() {

        return new CustomTypeSafeMatcher<ParseResult<?, ?>>("parse result has no more input") {
            @Override
            protected boolean matchesSafely(final ParseResult<?, ?> item) {

                return item.getNext().isAtEnd();
            }
        };
    }

    public static <T> Matcher<ParseResult<?, T>> hasResult(final Matcher<T> resultMatcher) {

        return new FeatureMatcher<ParseResult<?, T>, T>(resultMatcher, "parse result", "parse result") {

            @Override
            protected T featureValueOf(final ParseResult<?, T> actual) {

                return actual.get();
            }
        };
    }
}
