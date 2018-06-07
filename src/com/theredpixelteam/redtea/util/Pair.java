/*
 * Pair.java
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

package com.theredpixelteam.redtea.util;

import java.util.Objects;

public class Pair<T, E> {
    public Pair()
    {
    }

    public Pair(T first, E second)
    {
        this.first = first;
        this.second = second;
    }

    public T first(T first)
    {
        T old = this.first;
        this.first = first;
        return old;
    }

    public E second(E second)
    {
        E old = this.second;
        this.second = second;
        return old;
    }

    public T first()
    {
        return this.first;
    }

    public E second()
    {
        return this.second;
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof Pair))
            return false;

        Pair<?, ?> _object = (Pair<?, ?>) object;

        return _object.first.equals(first) && _object.second.equals(second);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(first, second);
    }

    private T first;

    private E second;
}
