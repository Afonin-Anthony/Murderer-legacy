/*
 * The MIT License
 *
 * Copyright (c) 2024 Anthony Afonin
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

package com.anon987666.murderer;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

public final class Settings {

	@Config(modid = MurdererMod.MODID, name = "Killer color")
	public static class KillerColor {

		@Name("Red")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int red = 255;

		@Name("Green")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int green = 0;

		@Name("Blue")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int blue = 0;

		@Name("Alpha")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int alpha = 255;
	}

	@Config(modid = MurdererMod.MODID, name = "Detective color")
	public static class DetectiveColor {

		@Name("Red")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int red = 255;

		@Name("Green")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int green = 255;

		@Name("Blue")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int blue = 0;

		@Name("Alpha")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int alpha = 255;
	}

	@Config(modid = MurdererMod.MODID, name = "Innocents color")
	public static class InnocentsColor {

		@Name("Red")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int red = 0;

		@Name("Green")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int green = 255;

		@Name("Blue")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int blue = 0;

		@Name("Alpha")
		@RangeInt(min = 0, max = 255)
		@SlidingOption
		public static int alpha = 255;
	}

	private Settings() {
		throw new AssertionError("No Settings instances for you!");
	}
}
