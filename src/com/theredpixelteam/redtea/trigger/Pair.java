package com.theredpixelteam.redtea.trigger;

public class Pair {
    Pair(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }

    public static <T> Pair of(String name, T value)
    {
        return new Pair(name, value);
    }

    final String name;

    final Object value;
}
