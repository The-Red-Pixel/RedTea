/*
 * Implication.java
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

public final class Implication<T> {
    public static Implication<Void> fake()
    {
        return of(false);
    }

    public static Implication<Void> of()
    {
        return of(true);
    }

    public static Implication<Void> of(boolean flag)
    {
        return new Implication<>(flag);
    }

    public static <T, X extends Throwable> Implication<Void> of(T t, PredicateWithException<T, X> predicate) throws X
    {
        return new Implication<>(predicate.test(t));
    }

    public static <T, X1 extends Throwable, X2 extends Throwable> Implication<Void> of(SupplierWithException<T, X1> supplier,
                                                                                 PredicateWithException<T, X2> predicate)
            throws X1, X2
    {
        return new Implication<>(predicate.test(supplier.get()));
    }

    public static <T> Implication<T> fake(T implicated)
    {
        return of(implicated, false);
    }

    public static <T> Implication<T> of(T implicated)
    {
        return of(implicated, true);
    }

    public static <T> Implication<T> of(T implicated, boolean flag)
    {
        return new Implication<>(implicated, flag);
    }

    public static <T, V, X extends Throwable> Implication<T> of(T implicated,
                                                                V t,
                                                                PredicateWithException<V, X> predicate) throws X
    {
        return new Implication<>(implicated, predicate.test(t));
    }

    public static <T, V, X1 extends Throwable, X2 extends Throwable> Implication<T> of(T implicated,
                                                                                       SupplierWithException<V, X1> supplier,
                                                                                       PredicateWithException<V, X2> predicate)
            throws X1, X2
    {
        return new Implication<>(implicated, predicate.test(supplier.get()));
    }

    Implication(boolean expressOrNot)
    {
        this(null, expressOrNot);
    }

    Implication(T implicated, boolean expressOrNot)
    {
        this.expressOrNot = expressOrNot;
        this.implicated = implicated;
    }

    public <X extends Throwable> T throwException(X e) throws X
    {
        if(expressOrNot)
            throw e;
        return implicated;
    }

    public <X extends Throwable, X1 extends Throwable> T throwException(SupplierWithException<X, X1> exceptionSupplier)
            throws X, X1
    {
        if(expressOrNot)
            throw exceptionSupplier.get();
        return implicated;
    }

    public <X extends Throwable> T perform(ProcedureWithException<X> procedure)
            throws X
    {
        if(expressOrNot)
            procedure.run();
        return implicated;
    }

    public <V, X extends Throwable> T perform(V value, ConsumerWithException<V, X> consumer)
            throws X
    {
        if(expressOrNot)
            consumer.accept(value);
        return implicated;
    }

    public <V, X1 extends Throwable, X2 extends Throwable> T perform(SupplierWithException<V, X1> supplier,
                                                                     ConsumerWithException<V, X2> consumer)
            throws X1, X2
    {
        if(expressOrNot)
            consumer.accept(supplier.get());
        return implicated;
    }

    public <V1, V2, X extends Throwable> T perform(V1 value1,
                                                   V2 value2,
                                                   BiConsumerWithException<V1, V2, X> consumer)
            throws X
    {
        if(expressOrNot)
            consumer.accept(value1, value2);
        return implicated;
    }

    public <V1, V2, X1 extends Throwable, X extends Throwable> T perform(SupplierWithException<V1, X1> value1Supplier,
                                                                         V2 value2,
                                                                         BiConsumerWithException<V1, V2, X> consumer)
            throws X1, X
    {
        if(expressOrNot)
            consumer.accept(value1Supplier.get(), value2);
        return implicated;
    }

    public <V1, V2, X2 extends Throwable, X extends Throwable> T perform(V1 value1,
                                                                         SupplierWithException<V2, X2> value2Supplier,
                                                                         BiConsumerWithException<V1, V2, X> consumer)
            throws X2, X
    {
        if(expressOrNot)
            consumer.accept(value1, value2Supplier.get());
        return implicated;
    }

    public <V1, V2, X1 extends Throwable, X2 extends Throwable, X extends Throwable> T perform(SupplierWithException<V1, X1> value1Supplier,
                                                                                               SupplierWithException<V2, X2> value2Supplier,
                                                                                               BiConsumerWithException<V1, V2, X> consumer)
            throws X1, X2, X
    {
        if(expressOrNot)
            consumer.accept(value1Supplier.get(), value2Supplier.get());
        return implicated;
    }

    public T escape()
    {
        return implicated;
    }

    private final T implicated;

    private final boolean expressOrNot;
}
