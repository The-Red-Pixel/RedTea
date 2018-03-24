package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface ConsumerWithException<T, X extends Throwable> {
    void accept(T t) throws X;

    default ConsumerWithException<T, X> andThen(ConsumerWithException<T, X> after)
    {
        Objects.requireNonNull(after, "after");
        return (T t) -> { accept(t); after.accept(t); };
    }
}
