package de.leanovate.routeegenerator.combinators;

public abstract class GenericInput<E> extends Input<GenericInput<E>> {
    public abstract E getFirst();
}
