package com.theredpixelteam.redtea.predication;

import com.theredpixelteam.redtea.function.ConsumerWithException;
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

        public <X extends Throwable> CurrentCondition<H> completelyIf()
        {
            return this;
        }

        public <X extends Throwable> void orElse(ConsumerWithException<MultiPredicate<?, H>.Current, X> consumer) throws X
        {
            if(!flag)
                consumer.accept(current);
        }

        public <X extends Throwable> void orElse(ProcedureWithException<X> procedure) throws X
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
