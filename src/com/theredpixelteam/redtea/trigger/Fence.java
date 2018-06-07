/*
 * Fence.java
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
