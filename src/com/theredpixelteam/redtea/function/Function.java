package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> extends FunctionWithThrowable<T, R, RuntimeException> {
    default java.util.function.Function<T, R> plain()
    {
        return this::apply;
    }

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before)
    {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
