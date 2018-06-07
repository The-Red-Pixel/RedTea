/*
 * Condition.java
 *
 * This file is part of RedTea, licensed under the MIT License (MIT).
 *
 * Copyright (C) The Red Pixel <theredpixelteam.com>
 * Copyright (C) KuCrO3 Studio <kucro3.org>
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
