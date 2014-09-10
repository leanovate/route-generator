package de.leanovate.routeegenerator.combinators;

@FunctionalInterface
public interface CharFunction<R> {
    public R apply(char ch);
}
