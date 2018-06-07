/*
 * ExceptionHandlerGroup.java
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

package com.theredpixelteam.redtea.trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ExceptionHandlerGroup {
    public ExceptionHandlerGroup()
    {
    }

    public ExceptionHandlerGroup(ExceptionHandler globalHandler)
    {
        handlers.add(new GlobalExceptionHandleriInGroup(globalHandler));
    }

    public boolean handle(Object trigger, Exception exception)
    {
        for(HandlerInGroup handler : handlers)
            if(handler.handle(trigger, exception))
                return true;

        return false;
    }

    public void clear()
    {
        handlers.clear();
    }

    public static ExceptionHandlerGroup of()
    {
        return new ExceptionHandlerGroup();
    }

    public static ExceptionHandlerGroup of(ExceptionHandler globalHandler)
    {
        return new ExceptionHandlerGroup(globalHandler);
    }

    public ExceptionHandlerGroup with()
    {
        return this;
    }

    public ExceptionHandlerGroup with(
            ExceptionHandler globalHandler)
    {
        handlers.add(new GlobalExceptionHandleriInGroup(globalHandler));
        return this;
    }

    public <T> ExceptionHandlerGroup with(
            Class<T> triggerType,
            ExceptionHandler handler)
    {
        handlers.add(new NormalExceptionHandlerInGroup(triggerType::isInstance, handler));
        return this;
    }

    public ExceptionHandlerGroup with(
            Predicate<Object> triggerPredicate,
            ExceptionHandler handler)
    {
        handlers.add(new NormalExceptionHandlerInGroup(triggerPredicate, handler));
        return this;
    }

    public <T, X extends Exception> ExceptionHandlerGroup with(
            Class<T> triggerType,
            Class<X> exceptionType,
            SpecializedExceptionHandler<X> handler)
    {
        handlers.add(new SpecializedExceptionHandlerInGroup(triggerType::isInstance, exceptionType::isInstance, handler));
        return this;
    }

    public <T> ExceptionHandlerGroup with(
            Class<T> triggerType,
            Predicate<Exception> exceptionPredicate,
            SpecializedExceptionHandler<Exception> handler)
    {
        handlers.add(new SpecializedExceptionHandlerInGroup(triggerType::isInstance, exceptionPredicate, handler));
        return this;
    }

    public <T> ExceptionHandlerGroup with(
            Class<T> triggerType,
            Predicate<Exception> exceptionPredicate,
            ExceptionHandler handler)
    {
        return with(triggerType, exceptionPredicate, (SpecializedExceptionHandler<Exception>) handler::handle);
    }

    public <X extends Exception> ExceptionHandlerGroup with(
            Predicate<Object> triggerPredicate,
            Class<X> exceptionType,
            SpecializedExceptionHandler<X> handler)
    {
        handlers.add(new SpecializedExceptionHandlerInGroup(triggerPredicate, exceptionType::isInstance, handler));
        return this;
    }

    public ExceptionHandlerGroup with(
            Predicate<Object> triggerPredicate,
            Predicate<Exception> exceptionPredicate,
            ExceptionHandler handler)
    {
        return with(triggerPredicate, exceptionPredicate, (SpecializedExceptionHandler<Exception>) handler::handle);
    }

    public ExceptionHandlerGroup with(
            Predicate<Object> triggerPredicate,
            Predicate<Exception> exceptionPredicate,
            SpecializedExceptionHandler<Exception> handler)
    {
        handlers.add(new SpecializedExceptionHandlerInGroup(triggerPredicate, exceptionPredicate, handler));
        return this;
    }

    private List<HandlerInGroup> handlers = new ArrayList<>();

    private static interface HandlerInGroup
    {
        boolean handle(Object trigger, Exception exception);
    }

    private class NormalExceptionHandlerInGroup implements HandlerInGroup
    {
        NormalExceptionHandlerInGroup(
                Predicate<Object> triggerPredicate,
                ExceptionHandler handler)
        {
            this.triggerPredicate = triggerPredicate;
            this.handler = handler;
        }

        @Override
        public boolean handle(Object trigger, Exception exception)
        {
            if(!triggerPredicate.test(trigger))
                return false;

            handler.handle(trigger, exception);
            return true;
        }

        private final Predicate<Object> triggerPredicate;

        private final ExceptionHandler handler;
    }

    @SuppressWarnings("unchecked")
    private class SpecializedExceptionHandlerInGroup implements HandlerInGroup
    {
        <X extends Exception> SpecializedExceptionHandlerInGroup(
                Predicate<Object> triggerPredicate,
                Predicate<Exception> exceptionPredicate,
                SpecializedExceptionHandler<X> handler)
        {
            this.triggerPredicate = triggerPredicate;
            this.exceptionPredicate = exceptionPredicate;
            this.handler = (SpecializedExceptionHandler) handler;
        }

        @Override
        public boolean handle(Object trigger, Exception exception)
        {
            if(!triggerPredicate.test(trigger))
                return false;

            if(!exceptionPredicate.test(exception))
                return false;

            handler.handle(trigger, exception);
            return true;
        }

        private final Predicate<Object> triggerPredicate;

        private final Predicate<Exception> exceptionPredicate;

        private final SpecializedExceptionHandler<Exception> handler;
    }

    private class GlobalExceptionHandleriInGroup implements HandlerInGroup
    {
        GlobalExceptionHandleriInGroup(ExceptionHandler handler)
        {
            this.handler = handler;
        }

        @Override
        public boolean handle(Object trigger, Exception exception)
        {
            handler.handle(trigger, exception);
            return true;
        }

        private final ExceptionHandler handler;
    }
}
