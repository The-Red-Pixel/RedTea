/*
 * ExceptionHandlerGroup.java
 *
 * Copyright (C) 2018 The Red Pixel <theredpixelteam.com>
 * Copyright (C) 2018 KuCrO3 Studio <kucro3.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
