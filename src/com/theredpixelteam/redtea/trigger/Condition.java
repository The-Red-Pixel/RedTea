/*
 * Condition.java
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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class Condition<T> {
    public Condition()
    {
    }

    public boolean bind(Predicate<T> predicate, Pipeline pipeline)
    {
        return set.add(new Binded<>(predicate, pipeline));
    }

    public boolean unbind(Pipeline pipeline)
    {
        return set.remove(pipeline);
    }

    public int trigger(T object, TriggerContext context)
    {
        int i = 0;

        for(Binded<T> binded : set)
            if(binded.trigger(object, context))
                i++;

        return i;
    }

    private final Set<Binded<T>> set = new HashSet<>();

    private static class Binded<T>
    {
        Binded(Predicate<T> predicate, Pipeline pipeline)
        {
            this.predicate = predicate;
            this.pipeline = pipeline;
        }

        boolean trigger(T object, TriggerContext context)
        {
            if(predicate.test(object))
            {
                pipeline.trigger(context);
                return true;
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            return pipeline.hashCode();
        }

        @Override
        public boolean equals(Object object)
        {
            if(object == null)
                return false;

            if(object instanceof Pipeline)
                return pipeline == object;

            if(!(object instanceof Binded))
                return false;

            Binded<T> binded = (Binded<T>) object;

            return pipeline == binded.pipeline;
        }

        private final Predicate<T> predicate;

        private final Pipeline pipeline;
    }
}
