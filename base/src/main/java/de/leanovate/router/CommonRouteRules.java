package de.leanovate.router;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CommonRouteRules {
    public static <Q,R> boolean prefix(RouteMatchingContext<Q,R> context, String prefix,
            Function<RouteMatchingContext, Boolean> onMatch) {

        if (context.path.startsWith(prefix)) {
            return onMatch.apply(context.withPath(context.path.substring(prefix.length())));
        } else {
            return false;
        }
    }

    public static <Q,R> boolean end(RouteMatchingContext<Q,R> context, Function<RouteMatchingContext, Boolean> onMatch) {

        if (context.path.isEmpty() || "/".equals(context.path)) {
            return onMatch.apply(context.withPath(""));
        } else {
            return false;
        }
    }

    public static <Q,R> boolean stringSegment(RouteMatchingContext<Q,R> context,
            BiFunction<RouteMatchingContext, String, Boolean> onMatch) {

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

    public static <Q,R> boolean remaining(RouteMatchingContext<Q,R> context,
            BiFunction<RouteMatchingContext, String, Boolean> onMatch) {

        return onMatch.apply(context.withPath(""), context.path);
    }

    public static <Q,R> boolean method(RouteMatchingContext<Q,R> context, String method,
            Function<RouteMatchingContext, Boolean> onMatch) {

        if (method.equalsIgnoreCase(context.method)) {
            return onMatch.apply(context);
        } else {
            return false;
        }
    }

    public static <Q,R> Optional<String> stringQuery(RouteMatchingContext<Q,R> context, String name) {

        return context.request.getQueryParam(name);
    }

    public static <Q,R> OptionalInt intQuery(RouteMatchingContext<Q,R> context, String name) {

        return context.request.getQueryParam(name)
                .map((str) -> OptionalInt.of(Integer.parseInt(str))).orElse(OptionalInt.empty());
    }
}
