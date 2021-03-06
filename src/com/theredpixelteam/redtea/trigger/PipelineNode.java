/*
 * PipelineNode.java
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

public final class PipelineNode extends Appendable {
    PipelineNode(NormalTrigger trigger, PipelineHead head, Fence fence)
    {
        this.trigger = trigger;
        this.head = head;
        this.fence = fence;

        if(fence != null)
            fence.initializeOwner(this);
    }

    public PipelineNode then(NormalTrigger trigger)
    {
        return then(trigger, null);
    }

    public PipelineNode then(NormalTrigger trigger, Fence fence)
    {
        return super.then(trigger, head, fence);
    }

    public PipelineTerminal then(TerminalTrigger trigger)
    {
        return then(trigger, null);
    }

    public PipelineTerminal then(TerminalTrigger trigger, Fence fence)
    {
        return super.then(trigger, head, fence);
    }

    @Override
    void trigger(TriggerContext context)
    {
        if(fence != null && !fence.dismantling && !fence.isDismantled())
            fence.fence(context);
        else try {
            if (trigger.trigger(context))
                super.next.trigger(context);
        } catch (TriggerException unhandled) {
            throw unhandled;
        } catch (Exception e) {
            if(!head.handlers.handle(this, e))
                throw new TriggerException(this, e);
        }
    }

    private final Fence fence;

    private final PipelineHead head;

    private final NormalTrigger trigger;
}
