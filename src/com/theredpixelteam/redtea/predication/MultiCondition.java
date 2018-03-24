package com.theredpixelteam.redtea.predication;

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

    public void apply(T t)
    {

    }

    public void apply(T t, H... handles)
    {

    }

    private final MultiPredicate<T, H> predicate;
}
