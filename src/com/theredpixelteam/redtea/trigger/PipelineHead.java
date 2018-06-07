/*
 * PipelineHead.java
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

public class PipelineHead extends Appendable {
    PipelineHead(String name, ExceptionHandlerGroup handlers)
    {
        this.name = name;
        this.handlers = handlers;
    }

    public PipelineNode then(NormalTrigger trigger)
    {
        return then(trigger, null);
    }

    public PipelineNode then(NormalTrigger trigger, Fence fence)
    {
        return super.then(trigger, this, fence);
    }

    public PipelineTerminal then(TerminalTrigger trigger)
    {
        return then(trigger, null);
    }

    public PipelineTerminal then(TerminalTrigger trigger, Fence fence)
    {
        return super.then(trigger, this, fence);
    }

    public String getName()
    {
        return name;
    }

    @Override
    void trigger(TriggerContext context)
    {
        super.next.trigger(context);
    }

    String name;

    final ExceptionHandlerGroup handlers;
}
