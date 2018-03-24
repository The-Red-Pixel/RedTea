package com.theredpixelteam.redtea.function;

@FunctionalInterface
public interface Supplier<T> extends SupplierWithException<T, RuntimeException> {
}
