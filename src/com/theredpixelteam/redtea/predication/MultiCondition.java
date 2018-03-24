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

    public final CurrentCondition apply(T t)
    {
        return new CurrentCondition<>(predicate.test(t));
    }

    @SafeVarargs
    public final CurrentCondition apply(T t, H... handles)
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

        public MultiPredicate<?, H>.Current getCurrent()
        {
            return current;
        }

        private boolean flag;

        private final MultiPredicate<?, H>.Current current;
    }
}
