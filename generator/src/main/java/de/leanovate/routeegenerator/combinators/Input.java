package de.leanovate.routeegenerator.combinators;

public abstract class Input<Self extends Input<?>> {
    public abstract Self getRest();

    public abstract Position getPosition();

    public abstract boolean isAtEnd();
}
