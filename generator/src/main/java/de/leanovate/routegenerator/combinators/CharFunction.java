package de.leanovate.routegenerator.combinators;

@FunctionalInterface
public interface CharFunction<R> {
    public R apply(char ch);
}
