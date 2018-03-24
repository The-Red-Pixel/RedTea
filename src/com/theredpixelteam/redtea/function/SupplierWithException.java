package com.theredpixelteam.redtea.function;

@FunctionalInterface
public interface SupplierWithException<T, X extends Throwable> {
    T get() throws X;
}
