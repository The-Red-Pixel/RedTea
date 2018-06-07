package com.theredpixelteam.redtea.util;

import java.util.Objects;

public class Pair<T, E> {
    public Pair()
    {
    }

    public Pair(T first, E second)
    {
        this.first = first;
        this.second = second;
    }

    public T first(T first)
    {
        T old = this.first;
        this.first = first;
        return old;
    }

    public E second(E second)
    {
        E old = this.second;
        this.second = second;
        return old;
    }

    public T first()
    {
        return this.first;
    }

    public E second()
    {
        return this.second;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof Pair))
            return false;

        Pair<?, ?> _object = (Pair<?, ?>) object;

        return _object.first.equals(first) && _object.second.equals(second);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second);
    }

    private T first;

    private E second;
}
