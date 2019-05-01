/*
 * FilteredEnumeration.java
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

import com.theredpixelteam.redtea.function.Predicate;

import java.util.Enumeration;

public class FilteredEnumeration<E> implements Enumeration<E> {
    public static <E> FilteredEnumeration<E> of(Enumeration<E> enumeration, Predicate<E> predicate)
    {
        return new FilteredEnumeration<>(enumeration, predicate);
    }

    FilteredEnumeration(Enumeration<E> enumeration, Predicate<E> predicate)
    {
        this.enumeration = enumeration;
        this.predicate = predicate;
    }

    @Override
    public boolean hasMoreElements()
    {
        if (nextElement != null)
            return true;

        while (enumeration.hasMoreElements())
        {
            E element = enumeration.nextElement();

            if (!predicate.test(element))
                continue;

            nextElement = element;
            return true;
        }

        return false;
    }

    @Override
    public E nextElement()
    {
        if (nextElement == null)
            return enumeration.nextElement();

        E element = nextElement;
        nextElement = null;

        return element;
    }

    private E nextElement;

    private final Enumeration<E> enumeration;

    private final Predicate<E> predicate;
}
