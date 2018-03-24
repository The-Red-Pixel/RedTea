package com.theredpixelteam.redtea.predication;

import java.util.Objects;
import java.util.function.Predicate;

public class NamedPredicate<T, H> implements Predicate<T> {
    public static <T, H> NamedPredicate<T, H> of(H handle, Predicate<T> predicate)
    {
        return new NamedPredicate<>(handle, predicate);
    }

    protected NamedPredicate(H handle, Predicate<T> predicate)
    {
        this.handle = Objects.requireNonNull(handle, "handle");
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public boolean test(T t)
    {
        return this.predicate.test(t);
    }

    public H getHandle()
    {
        return this.handle;
    }

    @Override
    public int hashCode()
    {
        return handle.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof NamedPredicate))
            return false;

        NamedPredicate<?, ?> instance = (NamedPredicate<?, ?>) object;

        return handle.equals(instance.handle);
    }

    protected final H handle;

    protected final Predicate<T> predicate;
}
