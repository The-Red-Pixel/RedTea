/*
 * VaguePair.java
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
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class VaguePair<T, E> extends Pair<T, E> {
    public VaguePair(int options)
    {
        init();
        apply(options);
    }

    public VaguePair(T first, E second, int options)
    {
        super(first, second);
        init();
        apply(options);
    }

    VaguePair(Pair<T, E> pair, int options)
    {
        this(options);
        this.referenced = pair;
    }

    public static <T, E> Pair<T, E> apply(Pair<T, E> pair, int options)
    {
        return new VaguePair<>(pair, options);
    }

    public static <T, E> void applyAll(Pair<T, E> pairs[], int options)
    {
        for(int i = 0; i < pairs.length; i++)
            pairs[i] = apply(pairs[i], options);
    }

    private void init()
    {
        MANIPULATORS[0].accept(this);
        MANIPULATORS[3].accept(this);
        MANIPULATORS[6].accept(this);
    }

    private void apply(int option)
    {
        for(int i = 0; i < MANIPULATORS.length; i++)
            if(((option >>> i) & 0x0001) != 0)
                MANIPULATORS[i].accept(this);
    }

    @Override
    public T first()
    {
        return referenced == null ? super.first() : referenced.first();
    }

    @Override
    public E second()
    {
        return referenced == null ? super.second() : referenced.second();
    }

    @Override
    public T first(T first)
    {
        return referenced == null ? super.first(first) : referenced.first(first);
    }

    @Override
    public E second(E second)
    {
        return referenced == null ? super.second(second) : referenced.second(second);
    }

    @Override
    public int hashCode()
    {
        return hash.applyAsInt(this);
    }

    @Override
    public boolean equals(Object object)
    {
        return object instanceof Pair<?, ?> && comparator.test(this, (Pair<?, ?>) object);
    }

    @Override
    public String toString()
    {
        return toString.apply(this);
    }

    @Override
    public Pair<T, E> copy()
    {
        return referenced == null ? super.copy() : new Pair<>(referenced.first(), referenced.second());
    }

    private String _toString()
    {
        return super.toString();
    }

    Pair<T, E> referenced;

    BiPredicate<VaguePair<?, ?>, Pair<?, ?>> comparator;

    ToIntFunction<VaguePair<?, ?>> hash;

    Function<VaguePair<?, ?>, String> toString;

    public static final int HASHCODE_BOTH = 0x0001;

    public static final int HASHCODE_FIRST_ONLY = 0x0002;

    public static final int HASHCODE_SECOND_ONLY = 0x0004;

    public static final int COMPARISON_BOTH = 0x0008;

    public static final int COMPARISON_FIRST_ONLY = 0x0010;

    public static final int COMPARISON_SECOND_ONLY = 0x0020;

    public static final int STRING_DEFAULT = 0x0040;

    public static final int STRING_FIRST_ONLY = 0x0080;

    public static final int STRING_SECOND_ONLY = 0x0100;

    private static final FilterManipulator[] MANIPULATORS = new FilterManipulator[] {
            (pair) -> pair.hash = (p) -> Objects.hash(p.first(), p.second()),
            (pair) -> pair.hash = (p) -> p.first().hashCode(),
            (pair) -> pair.hash = (p) -> p.second().hashCode(),

            (pair) -> pair.comparator = (p1, p2) ->
                    (p1.first() != null || p2.first() == null)
                    && (p1.second() != null || p2.second() == null)
                    && p1.first().equals(p2.first()) && p1.second().equals(p2.second()),
            (pair) -> pair.comparator = (p1, p2) ->
                    (p1.first() != null || p2.first() == null)
                    && p1.first().equals(p2.first()),
            (pair) -> pair.comparator = (p1, p2) ->
                    (p1.second() != null || p2.second() == null)
                    && p1.second().equals(p2.second()),

            (pair) -> pair.toString = VaguePair::_toString,
            (pair) -> pair.toString = (p) -> p.first().toString(),
            (pair) -> pair.toString = (p) -> p.second().toString()
    };

    private static interface FilterManipulator
    {
        void accept(VaguePair<?, ?> pair);
    }
}
