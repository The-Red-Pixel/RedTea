/*
 * MultiCondition.java
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

package com.theredpixelteam.redtea.predication;

import com.theredpixelteam.redtea.function.ConsumerWithException;
import com.theredpixelteam.redtea.function.Implication;
import com.theredpixelteam.redtea.function.ProcedureWithException;

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

        public final <X extends Throwable> void orElse(ConsumerWithException<MultiPredicate<?, H>.Current, X> consumer) throws X
        {
            if(!flag)
                consumer.accept(current);
        }

        public final <X extends Throwable> void orElse(ProcedureWithException<X> procedure) throws X
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
