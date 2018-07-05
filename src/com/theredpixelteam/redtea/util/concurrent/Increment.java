package com.theredpixelteam.redtea.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class Increment {
    public int get()
    {
        return value.get();
    }

    public void inc()
    {
        value.incrementAndGet();
    }

    public int getAndInc()
    {
        return value.getAndIncrement();
    }

    public int incAndGet()
    {
        return value.incrementAndGet();
    }

    private final AtomicInteger value = new AtomicInteger();
}
