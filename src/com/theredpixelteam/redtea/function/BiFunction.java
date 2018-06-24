package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface BiFunction<T, U, R> extends BiFunctionWithThrowable<T, U, R, RuntimeException> {
    default java.util.function.BiFunction<T, U, R> plain()
    {
        return this::apply;
    }

    default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after)
    {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
