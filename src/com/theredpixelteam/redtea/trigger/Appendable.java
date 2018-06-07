/*
 * Appendable.java
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

abstract class Appendable extends Triggerable {
    void check()
    {
        if(this.next != null)
            throw new IllegalStateException("Already connected");
    }

   PipelineNode then(NormalTrigger trigger, PipelineHead head, Fence fence)
    {
        check();

        PipelineNode node = new PipelineNode(trigger, head, fence);
        this.next = node;
        return node;
    }

    PipelineTerminal then(TerminalTrigger trigger, PipelineHead head, Fence fence)
    {
        check();

        PipelineTerminal terminal = new PipelineTerminal(trigger, head, fence);
        this.next = terminal;
        return terminal;
    }

    Triggerable next;
}
