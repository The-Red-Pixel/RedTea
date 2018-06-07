/*
 * TriggerContext.java
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

package com.theredpixelteam.redtea.trigger;

import java.util.*;

@SuppressWarnings("unchecked")
public class TriggerContext {
    TriggerContext()
    {
    }

    public static TriggerContext of()
    {
        return new TriggerContext();
    }

    public static TriggerContext of(Pair... pairs)
    {
        TriggerContext context = of();
        for(Pair pair : pairs)
            context.set(pair.name, pair.value);
        return context;
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
        ArrayList<T> list = new ArrayList<>();
        for(Map.Entry<String, Object> entry : values.entrySet())
            if(type.isInstance(entry.getValue()))
                list.add((T) entry.getValue());
        return list.iterator();
    }

    private final LinkedHashMap<String, Object> values = new LinkedHashMap<>();
}
