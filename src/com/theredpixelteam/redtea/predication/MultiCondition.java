/*
 * MultiCondition.java
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

package com.theredpixelteam.redtea.predication;

import com.theredpixelteam.redtea.function.ConsumerWithThrowable;
import com.theredpixelteam.redtea.function.Implication;
import com.theredpixelteam.redtea.function.ProcedureWithThrowable;

public class MultiCondition<T, H> {
    public static <T, H> MultiCondition<T, H> of(MultiPredicate<T, H> predicate)
    {
        return new MultiCondition<>(predicate);
    }

    MultiCondition(MultiPredicate<T, H> predicate)
    {
        this.predicate = predicate;
    }

    public MultiPredicate<T, H> getPredicate()
    {
        return predicate;
    }

    public final CurrentCondition<H> apply(T t)
    {
        return new CurrentCondition<>(predicate.test(t));
    }

    @SafeVarargs
    public final CurrentCondition<H> apply(T t, H... handles)
    {
        return new CurrentCondition<>(predicate.test(t, handles));
    }

    private final MultiPredicate<T, H> predicate;

    public static class CurrentCondition<H>
    {
        CurrentCondition(MultiPredicate<?, H>.Current current)
        {
            this.current = current;
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> completelyIf(H... handles)
        {
            return Implication.of(this, flag = current.completelyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> partlyIf(H... handles)
        {
            return Implication.of(this, flag = current.partlyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> completelyOnlyIf(H... handles)
        {
            return Implication.of(this, flag = current.completelyOnlyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> partlyOnlyIf(H... handles)
        {
            return Implication.of(this, flag = current.partlyOnlyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> elseCompletelyIf(H... handles)
        {
            if(flag)
                return Implication.fake(this);
            return Implication.of(this, flag = current.completelyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> elsePartlyIf(H... handles)
        {
            if(flag)
                return Implication.fake(this);
            return Implication.of(this, flag = current.partlyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> elseCompletelyOnlyIf(H... handles)
        {
            if(flag)
                return Implication.fake(this);
            return Implication.of(this, flag = current.completelyOnlyIf(handles));
        }

        @SafeVarargs
        public final Implication<CurrentCondition<H>> elsePartlyOnlyIf(H... handles)
        {
            if(flag)
                return Implication.fake(this);
            return Implication.of(this, flag = current.partlyOnlyIf(handles));
        }

        public final <X extends Throwable> void orElse(ConsumerWithThrowable<MultiPredicate<?, H>.Current, X> consumer) throws X
        {
            if(!flag)
                consumer.accept(current);
        }

        public final <X extends Throwable> void orElse(ProcedureWithThrowable<X> procedure) throws X
        {
            if(!flag)
                procedure.run();
        }

        public final Implication<CurrentCondition<H>> orElse()
        {
            return Implication.of(this, !flag);
        }

        public MultiPredicate<?, H>.Current getCurrent()
        {
            return current;
        }

        private boolean flag;

        private final MultiPredicate<?, H>.Current current;
    }
}
