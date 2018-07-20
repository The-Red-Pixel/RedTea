/*
 * PredicateWithThrowable.java
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

package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface PredicateWithThrowable<T, X extends Throwable> {
    boolean test(T t) throws X;

    default PredicateWithThrowable<T, X> and(PredicateWithThrowable<T, X> other)
    {
        Objects.requireNonNull(other, "other");
        return (T t) -> test(t) && other.test(t);
    }

    default PredicateWithThrowable<T, X> negate()
    {
        return (T t) -> !test(t);
    }

    default PredicateWithThrowable<T, X> or(PredicateWithThrowable<T, X> other)
    {
        Objects.requireNonNull(other, "other");
        return (T t) -> test(t) || other.test(t);
    }

    static <T, X extends Throwable> PredicateWithThrowable<T, X> isEqual(Object targetRef)
    {
        return targetRef == null ? Objects::isNull : targetRef::equals;
    }
}
