/*
 * TriggerException.java
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

public class TriggerException extends RuntimeException {
    public TriggerException(Object source)
    {
        this.source = source;
    }

    public TriggerException(Object source, String message)
    {
        super(message);
        this.source = source;
    }

    public TriggerException(Object source, Throwable cause)
    {
        super(cause);
        this.source = source;
    }

    public TriggerException(Object source, String message, Throwable cause)
    {
        super(message, cause);
        this.source = source;
    }

    public Object getSourceTrigger()
    {
        return source;
    }

    private final Object source;
}
