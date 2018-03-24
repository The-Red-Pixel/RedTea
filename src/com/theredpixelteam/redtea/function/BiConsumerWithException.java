package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface BiConsumerWithException<T, U, X extends Throwable> {
    void accept(T t, U u) throws X;

    default BiConsumerWithException<T, U, X> andThen(BiConsumerWithException<T, U, X> after) throws X
    {
        Objects.requireNonNull(after, "after");
        return (T t, U u) -> { accept(t, u); after.accept(t, u); };
    }
}
