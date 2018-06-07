/*
 * Fence.java
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

import java.util.ArrayList;
import java.util.List;

public class Fence {
    public Fence()
    {
    }

    public boolean dismantleIfOwned()
    {
        if(triggerable == null)
            return false;
        dismantle();
        return true;
    }

    public synchronized void dismantle()
    {
        try {
            dismantling = true;

            if (dismantled)
                throw new IllegalStateException("Already dismantled");

            if (triggerable == null)
                throw new IllegalStateException("Not owned by a trigger");

            for (TriggerContext context : fenced)
                triggerable.trigger(context);

            fenced.clear();
            dismantled = true;
        } finally {
            dismantling = false;
        }
    }

    public boolean isDismantled()
    {
        return dismantled;
    }

    void initializeOwner(Triggerable triggerable)
    {
        if(this.triggerable != null)
            throw new IllegalStateException("Already owned by a trigger");
        this.triggerable = triggerable;
    }

    void fence(TriggerContext context)
    {
        fenced.add(context);
    }

    private boolean dismantled;

    boolean dismantling;

    private List<TriggerContext> fenced = new ArrayList<>();

    private Triggerable triggerable;
}
