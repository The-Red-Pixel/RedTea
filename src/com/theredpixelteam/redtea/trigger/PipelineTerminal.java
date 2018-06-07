/*
 * PipelineTerminal.java
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

public final class PipelineTerminal extends Triggerable {
    PipelineTerminal(TerminalTrigger trigger, PipelineHead head, Fence fence)
    {
        this.trigger = trigger;
        this.head = head;
        this.fence = fence;
    }

    public Pipeline end()
    {
        return new Pipeline(head);
    }

    @Override
    void trigger(TriggerContext context)
    {
        if(fence != null && !fence.dismantling && !fence.isDismantled())
            fence.fence(context);
        else try {
            trigger.trigger(context);
        } catch (TriggerException unhandled) {
            throw unhandled;
        } catch (Exception e) {
            if(!head.handlers.handle(this, e))
                throw new TriggerException(this, e);
        }
    }

    private final Fence fence;

    private final PipelineHead head;

    private final TerminalTrigger trigger;
}
