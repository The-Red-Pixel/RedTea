package com.theredpixelteam.redtea.util;

import java.util.Objects;

public class Vector4<T1, T2, T3, T4> {
    public Vector4()
    {
    }

    public Vector4(T1 first, T2 second, T3 third, T4 last)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.last = last;
    }

    public static <T1, T2, T3, T4> Vector4<T1, T2, T3, T4> of(T1 first, T2 second, T3 third, T4 last)
    {
        return new Vector4<>(first, second, third, last);
    }

    public T1 first()
    {
        return this.first;
    }

    public T2 second()
    {
        return this.second;
    }

    public T3 third()
    {
        return this.third;
    }

    public T4 last()
    {
        return this.last;
    }

    public T1 first(T1 first)
    {
        T1 old = this.first;
        this.first = first;
        return old;
    }

    public T2 second(T2 second)
    {
        T2 old = this.second;
        this.second = second;
        return old;
    }

    public T3 third(T3 third)
    {
        T3 old = this.third;
        this.third = third;
        return old;
    }

    public T4 last(T4 last)
    {
        T4 old = this.last;
        this.last = last;
        return old;
    }

    public Vector4<T1, T2, T3, T4> copy()
    {
        return new Vector4<>(first, second, third, last);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second, third, last);
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == this)
            return true;

        if(!(object instanceof Vector4))
            return false;

        Vector4<?, ?, ?, ?> vec4 = (Vector4<?, ?, ?, ?>) object;

        return Objects.equals(vec4.first, this.first)
                && Objects.equals(vec4.second, this.second)
                && Objects.equals(vec4.third, this.third)
                && Objects.equals(vec4.last, this.last);
    }

    private T1 first;

    private T2 second;

    private T3 third;

    private T4 last;
}
