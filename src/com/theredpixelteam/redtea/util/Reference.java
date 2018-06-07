/*
 * Reference.java
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

package com.theredpixelteam.redtea.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Reference<T> implements Supplier<T>, Consumer<T> {
	public Reference(T value)
	{
		this.value = value;
	}
	
	public Reference()
	{
	}
	
	@Override
	public T get()
	{
		return value;
	}
	
	public void set(T value)
	{
		this.value = value;
	}
	
	@Override
	public void accept(T t) 
	{
		set(t);
	}
	
	private T value;
}
