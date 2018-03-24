package com.theredpixelteam.redtea.function;

import java.util.Objects;

@FunctionalInterface
public interface ProcedureWithException<X extends Throwable> {
    void run() throws X;

    default ProcedureWithException<X> andThen(ProcedureWithException<X> after)
    {
        Objects.requireNonNull(after, "after");
        return () -> { run(); after.run(); };
    }
}
