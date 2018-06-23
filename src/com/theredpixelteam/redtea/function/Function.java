package com.theredpixelteam.redtea.function;

@FunctionalInterface
public interface Function<T, R> extends FunctionWithThrowable<T, R, RuntimeException> {
    default java.util.function.Function<T, R> plain()
    {
        return this::apply;
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
