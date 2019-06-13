/*
 * Cluster.java
 *
 * This file is part of RedTea, licensed under the MIT License (MIT).
 *
 * Copyright (C) 2018 The Red Pixel <theredpixelteam.com>
 * Copyright (C) 2018 KuCrO3 Studio <kucro3.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.theredpixelteam.redtea.util;

import java.util.*;

@SuppressWarnings("unchecked")
public class Cluster {
    Cluster()
    {
    }

    public static Cluster of()
    {
        return new Cluster();
    }

    public static Cluster of(Pair<String, ?>... pairs)
    {
        Cluster cluster = of();
        for(Pair<String, ?> pair : pairs)
            cluster.set(pair.first(), pair.second());
        return cluster;
    }

    public <T> Optional<T> put(String name, T value)
    {
        return Optional.ofNullable((T) values.put(name, value));
    }

    public <T> void set(String name, T value)
    {
        values.put(name, value);
    }

    public <T> Optional<T> get(String name)
    {
        return Optional.ofNullable((T) values.get(name));
    }

    public boolean contains(String name)
    {
        return values.containsKey(name);
    }

    public <T> Optional<T> first(Class<T> type)
    {
        for(Map.Entry<String, Object> entry : values.entrySet())
            if(type.isInstance(entry.getValue()))
                return Optional.of((T) entry.getValue());
        return Optional.empty();
    }

    public <T> Optional<T> last(Class<T> type)
    {
        T value = null;
        for(Map.Entry<String, Object> entry : values.entrySet())
            if(type.isInstance(entry.getValue()))
                value = (T) entry.getValue();
        return Optional.ofNullable(value);
    }

    public <T> Iterator<T> iterator(Class<T> type)
    {
        return new Iter<>(type);
    }

    private final class Iter<T> implements Iterator<T>
    {
        Iter(Class<T> type)
        {
            this.type = type;
            this.valueIterator = values.values().iterator();
        }

        @Override
        public boolean hasNext()
        {
            if (next != null)
                return true;

            while (valueIterator.hasNext())
            {
                Object nextVal = valueIterator.next();

                if (type.isInstance(nextVal))
                {
                    next = (T) nextVal;

                    return true;
                }
            }

            return false;
        }

        @Override
        public T next()
        {
            if (!hasNext())
                throw new NoSuchElementException();

            T next = this.next;
            this.next = null;

            return next;
        }

        private T next;

        private final Class<?> type;

        private final Iterator<Object> valueIterator;
    }

    private final LinkedHashMap<String, Object> values = new LinkedHashMap<>();
}
