/*
 * ThreeStateOptional.java
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

import com.theredpixelteam.redtea.function.*;

import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings("unchecked")
public final class ThreeStateOptional<T> {
    private ThreeStateOptional()
    {
        this.empty = true;
        this.value = null;
    }

    private ThreeStateOptional(T value)
    {
        this.empty = false;
        this.value = value;
    }

    public static <T> ThreeStateOptional<T> empty()
    {
        return (ThreeStateOptional<T>) EMPTY;
    }

    public static <T> ThreeStateOptional<T> ofNull()
    {
        return (ThreeStateOptional<T>) NULL;
    }

    public static <T> ThreeStateOptional<T> ofNullable(T value)
    {
        return value == null ? ofNull() : new ThreeStateOptional<>(value);
    }

    public static <T> ThreeStateOptional<T> of(T value)
    {
        return new ThreeStateOptional<>(Objects.requireNonNull(value));
    }

    public boolean isEmpty()
    {
        return empty;
    }

    public boolean isPresent()
    {
        return value != null;
    }

    public boolean isNull()
    {
        return !empty && value == null;
    }

    public T get()
    {
        if(isNull())
            throw new NoSuchElementException();
        return value;
    }

    public T getSilently()
    {
        return value;
    }

    public T nullableGet()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        return value;
    }

    public T whenNull(T value)
    {
        T t = nullableGet();
        return t == null ? value : t;
    }

    public T whenEmpty(T value)
    {
        return isEmpty() ? value : nullableGet();
    }

    public T when(T valueOnNull, T valueOnEmpty)
    {
        return isEmpty() ? valueOnEmpty : whenNull(valueOnNull);
    }

    public <X extends Throwable> T getWhenNull(SupplierWithThrowable<? extends T, X> supplier) throws X
    {
        Objects.requireNonNull(supplier);

        T t = nullableGet();
        return t == null ? supplier.get() : t;
    }

    public <X extends Throwable> T getWhenEmpty(SupplierWithThrowable<? extends T, X> supplier) throws X
    {
        Objects.requireNonNull(supplier);

        return isEmpty() ? supplier.get() : nullableGet();
    }

    public <X1 extends Throwable, X2 extends Throwable> T getWhen(SupplierWithThrowable<? extends T, X1> supplierOnNull,
                                                                  SupplierWithThrowable<? extends T, X2> supplierOnEmpty)
            throws X1, X2
    {
        Objects.requireNonNull(supplierOnNull);
        Objects.requireNonNull(supplierOnEmpty);

        return isEmpty() ? supplierOnEmpty.get() : getWhenNull(supplierOnNull);
    }

    public <X extends Throwable> ThreeStateOptional<T> throwIfPresent(Supplier<? extends X> exceptionSupplier) throws X
    {
        Objects.requireNonNull(exceptionSupplier);

        if(isPresent())
            throw exceptionSupplier.get();

        return this;
    }

    public <X extends Throwable> ThreeStateOptional<T> throwIfNull(Supplier<? extends X> exceptionSupplier) throws X
    {
        Objects.requireNonNull(exceptionSupplier);

        if(isNull())
            throw exceptionSupplier.get();

        return this;
    }

    public <X extends Throwable> ThreeStateOptional<T> throwIfEmpty(Supplier<? extends X> exceptionSupplier) throws X
    {
        Objects.requireNonNull(exceptionSupplier);

        if(isEmpty())
            throw exceptionSupplier.get();

        return this;
    }

    public <X1 extends Throwable, X2 extends Throwable> T throwIfNullOrEmpty(Supplier<? extends X1> exceptionSupplierOnNull,
                                                                             Supplier<? extends X2> exceptionSupplierOnEmpty)
            throws X1, X2
    {
        Objects.requireNonNull(exceptionSupplierOnNull);
        Objects.requireNonNull(exceptionSupplierOnEmpty);

        if(isEmpty())
            throw exceptionSupplierOnEmpty.get();
        if(isNull())
            throw exceptionSupplierOnNull.get();
        return value;
    }

    public <X extends Throwable> IfNotPresent<T> ifPresent(ConsumerWithThrowable<? super T, ? extends X> consumer) throws X
    {
        boolean b;
        if(b = isPresent())
            consumer.accept(get());
        return IfNotPresent.of(!b, this);
    }

    public <X extends Throwable> IfNotNull<T> ifNull(ProcedureWithThrowable<X> procedure) throws X
    {
        boolean b;
        if(b = isNull())
            procedure.run();
        return IfNotNull.of(!b, this);
    }

    public <X extends Throwable> IfNotEmpty<T> ifEmpty(ProcedureWithThrowable<X> procedure) throws X
    {
        boolean b;
        if(b = isEmpty())
            procedure.run();
        return IfNotEmpty.of(!b, this);
    }

    public ThreeStateOptional<T> filter(Predicate<? super T> predicate)
    {
        Objects.requireNonNull(predicate);

        if (isEmpty())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    public <U> ThreeStateOptional<U> map(Function<? super T, ? extends U> mapper)
    {
        Objects.requireNonNull(mapper);

        if (isEmpty())
            return empty();
        else
            return ThreeStateOptional.ofNullable(mapper.apply(value));
    }

    public <U> ThreeStateOptional<U> flatMap(Function<? super T, ThreeStateOptional<U>> mapper)
    {
        Objects.requireNonNull(mapper);

        if (isEmpty())
            return empty();
        else
            return Objects.requireNonNull(mapper.apply(value));
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == this)
            return true;

        if(!(object instanceof ThreeStateOptional<?>))
            return false;

        ThreeStateOptional<?> obj = (ThreeStateOptional<?>) object;

        return (obj.empty == empty) && Objects.equals(value, obj.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value);
    }

    private final boolean empty;

    private final T value;

    private static final ThreeStateOptional<?> NULL = new ThreeStateOptional<>(null);

    private static final ThreeStateOptional<?> EMPTY = new ThreeStateOptional<>();

    public static final class IfNotPresent<T> extends Optional.Implicated
    {
        private IfNotPresent(boolean flag, ThreeStateOptional<T> self)
        {
            super(flag);
            this.self = self;
        }

        static <T> IfNotPresent<T> of(boolean flag, ThreeStateOptional<T> self)
        {
            return flag ? new IfNotPresent<>(true, self) : (IfNotPresent<T>) FALSE;
        }

        public <X extends Throwable> Optional.Implicated orElseIfNull(ProcedureWithThrowable<? extends X> procedure) throws X
        {
            if(flag && self.isNull())
                procedure.run();
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        public <X extends Throwable> Optional.Implicated ofElseIfEmpty(ProcedureWithThrowable<? extends X> procedure) throws X
        {
            if(flag && self.isEmpty())
                procedure.run();
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        private static final IfNotPresent<?> FALSE = new IfNotPresent<>(false, null);

        private final ThreeStateOptional<T> self;
    }

    public static final class IfNotNull<T> extends Optional.Implicated
    {
        private IfNotNull(boolean flag, ThreeStateOptional<T> self)
        {
            super(flag);
            this.self = self;
        }

        static <T> IfNotNull<T> of(boolean flag, ThreeStateOptional<T> self)
        {
            return flag ? new IfNotNull<>(true, self) : (IfNotNull<T>) FALSE;
        }

        public <X extends Throwable> Optional.Implicated orElseIfPresent(ConsumerWithThrowable<? super T, ? extends X> consumer) throws X
        {
            if(flag && self.isPresent())
                consumer.accept(self.value);
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        public <X extends Throwable> Optional.Implicated ofElseIfEmpty(ProcedureWithThrowable<? extends X> procedure) throws X
        {
            if(flag && self.isEmpty())
                procedure.run();
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        private static final IfNotNull<?> FALSE = new IfNotNull<>(false, null);

        private final ThreeStateOptional<T> self;
    }

    public static final class IfNotEmpty<T> extends Optional.Implicated
    {
        private IfNotEmpty(boolean flag, ThreeStateOptional<T> self)
        {
            super(flag);
            this.self = self;
        }

        static <T> IfNotEmpty<T> of(boolean flag, ThreeStateOptional<T> self)
        {
            return flag ? new IfNotEmpty<>(true, self) : (IfNotEmpty<T>) FALSE;
        }

        public <X extends Throwable> Optional.Implicated orElseIfPresent(ConsumerWithThrowable<? super T, ? extends X> consumer) throws X
        {
            if(flag && self.isPresent())
                consumer.accept(self.value);
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        public <X extends Throwable> Optional.Implicated orElseIfNull(ProcedureWithThrowable<? extends X> procedure) throws X
        {
            if(flag && self.isNull())
                procedure.run();
            else
                return Optional.Implicated.of(flag);

            return Optional.Implicated.FALSE;
        }

        private static final IfNotEmpty<?> FALSE = new IfNotEmpty<>(false, null);

        private final ThreeStateOptional<T> self;
    }
}
