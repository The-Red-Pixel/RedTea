package com.theredpixelteam.redtea.function;

@FunctionalInterface
public interface Consumer<T> extends ConsumerWithException<T, RuntimeException> {
}
