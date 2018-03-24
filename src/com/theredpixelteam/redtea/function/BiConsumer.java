package com.theredpixelteam.redtea.function;

@FunctionalInterface
public interface BiConsumer<T, U> extends BiConsumerWithException<T, U, RuntimeException> {
}
