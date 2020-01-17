/*
 * Predication.java
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

import java.util.function.Supplier;

public final class Predication {
    private Predication()
    {
        throw new ShouldNotReachHere();
    }

    public static boolean isNull(Object obj)
    {
        return obj == null;
    }

    public static boolean isNonNull(Object obj)
    {
        return obj != null;
    }

    public static <T> T requireNonNull(T obj)
    {
        if (obj == null)
            throw new NullPointerException();

        return obj;
    }

    public static <T> T requireNonNull(T obj, String message)
    {
        if (obj == null)
            throw new NullPointerException(message);

        return obj;
    }

    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier)
    {
        if (obj == null)
            throw new NullPointerException(messageSupplier.get());

        return obj;
    }

    public static boolean isNegative(int value)
    {
        return value < 0;
    }

    public static boolean isPositive(int value)
    {
        return value > 0;
    }

    public static boolean isZero(int value)
    {
        return value == 0;
    }

    public static int requireNegative(int value)
    {
        if (value < 0)
            return value;

        throw new IllegalArgumentException();
    }

    public static int requireNegative(int value, String message)
    {
        if (value < 0)
            return value;

        throw new IllegalArgumentException(message);
    }

    public static int requireNegative(int value, Supplier<String> messageSupplier)
    {
        if (value < 0)
            return value;

        throw new IllegalArgumentException(messageSupplier.get());
    }

    public static int requireNonNegative(int value)
    {
        if (value < 0)
            throw new IllegalArgumentException();

        return value;
    }

    public static int requireNonNegative(int value, String message)
    {
        if (value < 0)
            throw new IllegalArgumentException(message);

        return value;
    }

    public static int requireNonNegative(int value, Supplier<String> messageSupplier)
    {
        if (value < 0)
            throw new IllegalArgumentException(messageSupplier.get());

        return value;
    }

    public static int requirePositive(int value)
    {
        if (value > 0)
            return value;

        throw new IllegalArgumentException();
    }

    public static int requirePositive(int value, String message)
    {
        if (value > 0)
            return value;

        throw new IllegalArgumentException(message);
    }

    public static int requirePositive(int value, Supplier<String> messageSupplier)
    {
        if (value > 0)
            return value;

        throw new IllegalArgumentException(messageSupplier.get());
    }

    public static int requireNonPositive(int value)
    {
        if (value > 0)
            throw new IllegalArgumentException();

        return value;
    }

    public static int requireNonPositive(int value, String message)
    {
        if (value > 0)
            throw new IllegalArgumentException(message);

        return value;
    }

    public static int requireNonPositive(int value, Supplier<String> messageSupplier)
    {
        if (value > 0)
            throw new IllegalArgumentException(messageSupplier.get());

        return value;
    }
}
