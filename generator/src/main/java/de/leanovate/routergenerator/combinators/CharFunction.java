package de.leanovate.routergenerator.combinators;

@FunctionalInterface
public interface CharFunction<R> {
    public R apply(char ch);
}
