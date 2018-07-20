/*
 * Vector4.java
 *
 * This file is part of RedTea, licensed under the MIT License (MIT).
 *
 * Copyright (C) 2018 The Red Pixel <theredpixelteam.com>
 * Copyright (C) 2018 KuCrO3 Studio <kucro3.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
