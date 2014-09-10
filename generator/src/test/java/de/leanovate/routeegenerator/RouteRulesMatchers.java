package de.leanovate.routeegenerator;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class RouteRulesMatchers {
    public static Matcher<RouteRules> hasPackage(final Matcher<? super String> packageMatcher) {
        return new FeatureMatcher<RouteRules, String>(packageMatcher, "package", "package") {

            @Override
            protected String featureValueOf(final RouteRules actual) {

                return actual.packageName;
            }
        };
    }
}
