/*
 * NamedPredicate.java
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

package com.theredpixelteam.redtea.predication;

import java.util.Objects;
import java.util.function.Predicate;

public class NamedPredicate<T, H> implements Predicate<T> {
    public static <T, H> NamedPredicate<T, H> of(H handle, Predicate<T> predicate)
    {
        return new NamedPredicate<>(handle, predicate);
    }

    protected NamedPredicate(H handle, Predicate<T> predicate)
    {
        this.handle = Objects.requireNonNull(handle, "handle");
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public boolean test(T t)
    {
        return this.predicate.test(t);
    }

    public H getHandle()
    {
        return this.handle;
    }

    @Override
    public int hashCode()
    {
        return handle.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if(!(object instanceof NamedPredicate))
            return false;

        NamedPredicate<?, ?> instance = (NamedPredicate<?, ?>) object;

        return handle.equals(instance.handle);
    }

    protected final H handle;

    protected final Predicate<T> predicate;
}
