/*
 * Pair.java
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

    public static <T, E> Pair<T, E> of(T first, E second)
    {
        return new Pair<>(first, second);
    }

    public T first()
    {
        return this.first;
    }

    public E second()
    {
        return this.second;
    }

    public Pair<T, E> copy()
    {
        return new Pair<>(first, second);
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
