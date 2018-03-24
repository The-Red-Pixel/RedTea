package com.theredpixelteam.redtea.function;

public final class Implication<T> {
    public static Implication<Void> of()
    {
        return of(true);
    }

    public static Implication<Void> of(boolean flag)
    {
        return new Implication<>(flag);
    }

    public static <T, X extends Throwable> Implication<Void> of(T t, PredicateWithException<T, X> predicate) throws X
    {
        return new Implication<>(predicate.test(t));
    }

    public static <T, X1 extends Throwable, X2 extends Throwable> Implication<Void> of(SupplierWithException<T, X1> supplier,
                                                                                 PredicateWithException<T, X2> predicate)
            throws X1, X2
    {
        return new Implication<>(predicate.test(supplier.get()));
    }

    Implication(boolean expressOrNot)
    {
        this(null, expressOrNot);
    }

    Implication(T implicated, boolean expressOrNot)
    {
        this.expressOrNot = expressOrNot;
        this.implicated = implicated;
    }

    public <X extends Throwable> T throwException(X e) throws X
    {
        if(expressOrNot)
            throw e;
        return implicated;
    }

    public <X extends Throwable, X1 extends Throwable> T throwException(SupplierWithException<X, X1> exceptionSupplier)
            throws X, X1
    {
        if(expressOrNot)
            throw exceptionSupplier.get();
        return implicated;
    }

    public <X extends Throwable> T perform(ProcedureWithException<X> procedure)
            throws X
    {
        if(expressOrNot)
            procedure.run();
        return implicated;
    }

    public <V, X extends Throwable> T perform(V value, ConsumerWithException<V, X> consumer)
            throws X
    {
        if(expressOrNot)
            consumer.accept(value);
        return implicated;
    }

    public <V, X1 extends Throwable, X2 extends Throwable> T perform(SupplierWithException<V, X1> supplier,
                                                                     ConsumerWithException<V, X2> consumer)
            throws X1, X2
    {
        if(expressOrNot)
            consumer.accept(supplier.get());
        return implicated;
    }

    public <V1, V2, X extends Throwable> T perform(V1 value1,
                                                   V2 value2,
                                                   BiConsumerWithException<V1, V2, X> consumer)
            throws X
    {
        if(expressOrNot)
            consumer.accept(value1, value2);
        return implicated;
    }

    public <V1, V2, X1 extends Throwable, X extends Throwable> T perform(SupplierWithException<V1, X1> value1Supplier,
                                                                         V2 value2,
                                                                         BiConsumerWithException<V1, V2, X> consumer)
            throws X1, X
    {
        if(expressOrNot)
            consumer.accept(value1Supplier.get(), value2);
        return implicated;
    }

    public <V1, V2, X2 extends Throwable, X extends Throwable> T perform(V1 value1,
                                                                         SupplierWithException<V2, X2> value2Supplier,
                                                                         BiConsumerWithException<V1, V2, X> consumer)
            throws X2, X
    {
        if(expressOrNot)
            consumer.accept(value1, value2Supplier.get());
        return implicated;
    }

    public <V1, V2, X1 extends Throwable, X2 extends Throwable, X extends Throwable> T perform(SupplierWithException<V1, X1> value1Supplier,
                                                                                               SupplierWithException<V2, X2> value2Supplier,
                                                                                               BiConsumerWithException<V1, V2, X> consumer)
            throws X1, X2, X
    {
        if(expressOrNot)
            consumer.accept(value1Supplier.get(), value2Supplier.get());
        return implicated;
    }

    public T escape()
    {
        return implicated;
    }

    private final T implicated;

    private final boolean expressOrNot;
}
