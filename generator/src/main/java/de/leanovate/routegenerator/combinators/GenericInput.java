package de.leanovate.routegenerator.combinators;

public abstract class GenericInput<E> extends Input<GenericInput<E>> {
    public abstract E getFirst();
}
