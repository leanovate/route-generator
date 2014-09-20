package de.leanovate.router;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Set of commonly used matching rules.
 */
public class CommonRouteRules {
    /**
     * Match a specific prefix of a path.
     *
     * @param context the current matching context
     * @param prefix the prefix to match
     * @param onMatch callback if the prefix is matched, the callback will receive a sub-context without the matched
     * prefix
     * @return {@code true} if the path matches the prefix and the {@code onMatch} callback returns {@code true} as well
     */
    public static <Q, R> boolean prefix(RouteMatchingContext<Q, R> context, String prefix,
            Function<RouteMatchingContext<Q, R>, Boolean> onMatch) {

        if (context.path.startsWith(prefix)) {
            return onMatch.apply(context.withPath(context.path.substring(prefix.length())));
        } else {
            return false;
        }
    }

    /**
     * Matches if the path does not have any more path-segments.
     *
     * @param context the current matching context
     * @param onMatch callback if the path does not contain any more segments, the callback will receive a sub-context
     * with an empty path
     * @return {@code true} if the path matches and the {@code onMatch} callback returns {@code true} as well
     */
    public static <Q, R> boolean end(RouteMatchingContext<Q, R> context,
            Function<RouteMatchingContext<Q, R>, Boolean> onMatch) {

        if (context.path.isEmpty() || "/".equals(context.path)) {
            return onMatch.apply(context.withPath(""));
        } else {
            return false;
        }
    }

    /**
     * Matches a generic string-valued non-empty path-segment.
     *
     * @param context the current matching context
     * @param onMatch callback if the path contains a non-empty path-segment, the callback will receive the extracted
     * string-value as well a context without the matched segment
     * @return {@code true} if the path matches and the {@code onMatch} callback returns {@code true} as well
     */
    public static <Q, R> boolean stringSegment(RouteMatchingContext<Q, R> context,
            BiFunction<RouteMatchingContext<Q, R>, String, Boolean> onMatch) {

        int begin = 0;

        if (!context.path.isEmpty() && context.path.charAt(0) == '/') {
            begin = 1;
        }
        int end = context.path.indexOf('/', begin);

        if (end > begin) {
            return onMatch.apply(context.withPath(context.path.substring(end)), context.path.substring(begin, end));
        } else if (end < 0 && context.path.length() > begin) {
            return onMatch.apply(context.withPath(""), context.path.substring(begin));
        } else {
            return false;
        }
    }

    /**
     * Matches all remaining path-segments
     *
     * @param context the current matching context
     * @param onMatch callback that will receive all remaining path-segments as well as a sub-context with an empty
     * path
     * @return {@code true} if the path matches and the {@code onMatch} callback returns {@code true} as well
     */
    public static <Q, R> boolean remaining(RouteMatchingContext<Q, R> context,
            BiFunction<RouteMatchingContext<Q, R>, String, Boolean> onMatch) {

        if (!context.path.isEmpty() && context.path.charAt(0) == '/') {
            return onMatch.apply(context.withPath(""), context.path.substring(1));
        } else {
            return onMatch.apply(context.withPath(""), context.path);
        }
    }

    /**
     * Matches a specific http request method.
     *
     * @param context the current matching context
     * @param method the desired request method
     * @param onMatch callback if the request method matches
     * @return {@code true} if the http request method matches and the {@code onMatch} callback returns {@code true} as
     * well
     */
    public static <Q, R> boolean method(RouteMatchingContext<Q, R> context, String method,
            Function<RouteMatchingContext<Q, R>, Boolean> onMatch) {

        if (method.equalsIgnoreCase(context.method)) {
            return onMatch.apply(context);
        } else {
            return false;
        }
    }

    /**
     * Extract an optional string-valued query parameter.
     *
     * @param context the current matching context
     * @param name the name of the desired query parameter
     * @return the extracted value of the query parameter
     */
    public static <Q, R> Optional<String> stringQuery(RouteMatchingContext<Q, R> context, String name) {

        return context.getQueryParam(name);
    }

    /**
     * Extract an optional integer-valued query parameter.
     *
     * @param context the current matching context
     * @param name the name of the desired query parameter
     * @return the extracted value of the query parameter
     */
    public static <Q, R> OptionalInt intQuery(RouteMatchingContext<Q, R> context, String name) {

        return context.getQueryParam(name)
                .map((str) -> OptionalInt.of(Integer.parseInt(str))).orElse(OptionalInt.empty());
    }
}
