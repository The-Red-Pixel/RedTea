package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface BiFunctionWithThrowable<T, U, R, X extends Throwable> {
    R apply(T t, U u) throws X;

    default <V> BiFunctionWithThrowable<T, U, V, X> andThen(FunctionWithThrowable<? super R, ? extends V, X> after)
    {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
