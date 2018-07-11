/*
 * OperationResult.java
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

import java.util.*;

public final class OperationResult<T> {
    public static <T> OperationResult<T> succeeded()
    {
        return succeeded(null);
    }

    public static <T> OperationResult<T> succeeded(T result)
    {
        return new OperationResult<>(ResultType.SUCCEEDED, result, null, null, null);
    }

    public static <T> OperationResult<T> failed()
    {
        return failed(null);
    }

    public static <T> OperationResult<T> failed(Object failure)
    {
        return new OperationResult<>(ResultType.FAILED, null, failure, null, null);
    }

    public static <T> OperationResult<T> failedWithException(Exception exception)
    {
        return failedWithException(exception, exception);
    }

    public static <T> OperationResult<T> failedWithException(Object failure, Exception exception)
    {
        return new OperationResult<>(ResultType.FAILED_WITH_EXCEPTION, null, failure, exception, null);
    }

    public static <T> OperationResult<T> noSuchObject()
    {
        return noSuchObject(null);
    }

    public static <T> OperationResult<T> noSuchObject(Object failure)
    {
        return new OperationResult<>(ResultType.NO_SUCH_OBJECT, null, failure, null, null);
    }

    public static <T> OperationResult<T> mismatch()
    {
        return mismatch(new Mismatches());
    }

    public static <T> OperationResult<T> mismatch(Mismatches mismatches)
    {
        return mismatch(null, mismatches);
    }

    public static <T> OperationResult<T> mismatch(Object failure, Mismatches mismatchs)
    {
        return new OperationResult<>(ResultType.MISMATCH, null, failure, null, new HashSet<>(mismatchs.mismatches));
    }

    private OperationResult(ResultType resultType, T result, Object failure, Exception exception, Collection<Mismatch> mismatches)
    {
        this.resultType = resultType;
        this.result = result;
        this.failure = failure;
        this.exception = exception;
        this.mismatches = mismatches;
    }

    public java.util.Optional<T> getReturn()
    {
        return java.util.Optional.ofNullable(result);
    }

    final boolean state(ResultType resultType)
    {
        return resultType.equals(this.resultType);
    }

    public boolean isFailed()
    {
        return state(ResultType.FAILED) || state(ResultType.FAILED_WITH_EXCEPTION);
    }

    public boolean isFailedWithNoException()
    {
        return state(ResultType.FAILED);
    }

    public boolean isFailedWithException()
    {
        return state(ResultType.FAILED_WITH_EXCEPTION);
    }

    public boolean isMismatched()
    {
        return state(ResultType.MISMATCH);
    }

    public boolean isSucceeded()
    {
        return state(ResultType.SUCCEEDED);
    }

    public boolean isNoSuchObject()
    {
        return state(ResultType.NO_SUCH_OBJECT);
    }

    public java.util.Optional<Object> getFailure()
    {
        return java.util.Optional.ofNullable(failure);
    }

    public java.util.Optional<Exception> getException()
    {
        return java.util.Optional.ofNullable(exception);
    }

    public Collection<Mismatch> getMismatches()
    {
        return mismatches == null ? Collections.emptyList() : Collections.unmodifiableCollection(mismatches);
    }

    public ResultType getResult()
    {
        return resultType;
    }

    private final ResultType resultType;

    private final T result;

    private final Object failure;

    private final Exception exception;

    private final Collection<Mismatch> mismatches;

    public enum ResultType
    {
        SUCCEEDED,
        FAILED,
        FAILED_WITH_EXCEPTION,
        NO_SUCH_OBJECT,
        MISMATCH
    }

    public static class Mismatch
    {
        public static Mismatches of(Object pattern, Object expected, Object provided)
        {
            return new Mismatches().and(pattern, expected, provided);
        }

        public Mismatch(Object pattern, Object expected, Object provided)
        {
            this.pattern = pattern;
            this.expected = expected;
            this.provided = provided;
        }

        public Object getPattern()
        {
            return pattern;
        }

        public Object getExpected()
        {
            return expected;
        }

        public Object getProvided()
        {
            return provided;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(pattern, expected, provided);
        }

        @Override
        public boolean equals(Object object)
        {
            if(!(object instanceof Mismatch))
                return false;

            Mismatch obj = (Mismatch) object;

            if(!obj.pattern.equals(pattern))
                return false;

            if(!obj.expected.equals(expected))
                return false;

            if(!obj.provided.equals(provided))
                return false;

            return true;
        }

        private final Object pattern;

        private final Object expected;

        private final Object provided;
    }

    public static class Mismatches
    {
        public Mismatches()
        {
            this.mismatches = new HashSet<>();
        }

        public Mismatches and(Object pattern, Object expected, Object provided)
        {
            return and(new Mismatch(pattern, expected, provided));
        }

        public Mismatches and(Mismatch mismatch)
        {
            mismatches.add(mismatch);
            return this;
        }

        public boolean remove(Mismatch mismatch)
        {
            return mismatches.remove(mismatch);
        }

        public boolean contains(Mismatch mismatch)
        {
            return mismatches.contains(mismatch);
        }

        public void clear()
        {
            mismatches.clear();
        }

        final HashSet<Mismatch> mismatches;
    }
}
