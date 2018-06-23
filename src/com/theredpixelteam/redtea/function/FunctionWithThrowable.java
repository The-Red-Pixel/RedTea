package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface FunctionWithThrowable<T, R, X extends Throwable> {
    R apply(T t) throws X;

    default <V> FunctionWithThrowable<V, R, X> compose(FunctionWithThrowable<? super V, ? extends T, X> before)
    {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> FunctionWithThrowable<T, V, X> andThen(FunctionWithThrowable<? super R, ? extends V, X> after)
    {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T, X extends Throwable> FunctionWithThrowable<T, T, X> identity() {
        return t -> t;
    }
}
