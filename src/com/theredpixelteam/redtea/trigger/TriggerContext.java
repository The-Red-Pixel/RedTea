/*
 * TriggerContext.java
 *
 * Copyright (C) 2018 The Red Pixel <theredpixelteam.com>
 * Copyright (C) 2018 KuCrO3 Studio <kucro3.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
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
