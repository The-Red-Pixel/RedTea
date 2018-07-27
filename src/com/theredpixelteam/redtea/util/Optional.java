/*
 * Optional.java
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
public final class Optional<T> {
    private Optional(T value)
    {
        this.value = value;
    }

    public static <T> Optional<T> empty()
    {
        return (Optional<T>) EMPTY;
    }

    public static <T> Optional<T> ofNullable(T object)
    {
        return object == null ? empty() : of(object);
    }

    public static <T> Optional<T> of(T object)
    {
        return new Optional<>(Objects.requireNonNull(object));
    }

    public T get()
    {
        if(value == null)
            throw new NoSuchElementException();
        return value;
    }

    public T getSilently()
    {
        return value;
    }

    public boolean isPresent()
    {
        return value != null;
    }

    public T orElse(T other)
    {
        return isPresent() ? value : other;
    }

    public <X extends Throwable> T orElseGet(SupplierWithThrowable<? extends T, ? extends X> other) throws X
    {
        return isPresent() ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
    {
        if(isPresent())
            return value;
        throw exceptionSupplier.get();
    }

    public <X extends Throwable> Optional<T> whenPresent(ConsumerWithThrowable<? super T, ? extends X> consumer) throws X
    {
        if(isPresent())
            consumer.accept(value);

        return this;
    }

    public <X extends Throwable> Optional<T> whenNotPresent(ProcedureWithThrowable<? extends X> procedure) throws X
    {
        if(!isPresent())
            procedure.run();

        return this;
    }

    public <X extends Throwable> IfNotPresent ifPresent(ConsumerWithThrowable<? super T, ? extends X> consumer) throws X
    {
        boolean b;
        if(b = (value != null))
            consumer.accept(value);
        return IfNotPresent.of(!b);
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> mapper)
    {
        Objects.requireNonNull(mapper);

        if (!isPresent())
            return empty();
        else
            return Optional.ofNullable(mapper.apply(value));
    }

    public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
    {
        Objects.requireNonNull(mapper);

        if (!isPresent())
            return empty();
        else
            return Objects.requireNonNull(mapper.apply(value));
    }

    public Optional<T> filter(Predicate<? super T> predicate)
    {
        Objects.requireNonNull(predicate);

        if (!isPresent())
            return this;
        else
            return predicate.test(value) ? this : empty();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == this)
            return true;

        if(!(object instanceof Optional<?>))
            return false;

        return Objects.equals(value, ((Optional<?>) object).value);
    }

    private final T value;

    private static final Optional<?> EMPTY = new Optional<>(null);

    public static class Implicated
    {
        Implicated(boolean flag)
        {
            this.flag = flag;
        }

        static Implicated of(boolean b)
        {
            return b ? TRUE : FALSE;
        }

        public <X extends Throwable> void orElse(ProcedureWithThrowable<? extends X> procedure) throws X
        {
            if(flag)
                procedure.run();
        }

        final boolean flag;

        static final Implicated TRUE = new Implicated(true);

        static final Implicated FALSE = new Implicated(false);
    }

    public static final class IfNotPresent extends Implicated
    {
        private IfNotPresent(boolean flag)
        {
            super(flag);
        }

        static IfNotPresent of(boolean b)
        {
            return b ? TRUE : FALSE;
        }

        static final IfNotPresent TRUE = new IfNotPresent(true);

        static final IfNotPresent FALSE = new IfNotPresent(false);
    }
}
