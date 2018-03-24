package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface PredicateWithException<T, X extends Throwable> {
    boolean test(T t) throws X;

    default PredicateWithException<T, X> and(PredicateWithException<T, X> other)
    {
        Objects.requireNonNull(other, "other");
        return (T t) -> test(t) && other.test(t);
    }

    default PredicateWithException<T, X> negate()
    {
        return (T t) -> !test(t);
    }

    default PredicateWithException<T, X> or(PredicateWithException<T, X> other)
    {
        Objects.requireNonNull(other, "other");
        return (T t) -> test(t) || other.test(t);
    }

    static <T, X extends Throwable> PredicateWithException<T, X> isEqual(Object targetRef)
    {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }
}
