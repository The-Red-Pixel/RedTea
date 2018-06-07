/*
 * NamedPredicate.java
 *
 * This file is part of RedTea, licensed under the MIT License (MIT).
 *
 * Copyright (C) The Red Pixel <theredpixelteam.com>
 * Copyright (C) KuCrO3 Studio <kucro3.org>
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
