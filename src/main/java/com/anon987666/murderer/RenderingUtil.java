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

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;

public final class RenderingUtil {

	private static final Minecraft MC = Minecraft.getMinecraft();

	private static final Tessellator TESSELLATOR = Tessellator.getInstance();

	private static final BufferBuilder BUILDER = TESSELLATOR.getBuffer();

	private static final double TWICE_PI = Math.PI * 2;

	private static final int CIRCLE_SEGMENTS = 30;

	private RenderingUtil() {
		throw new AssertionError("No RenderingUtil instances for you!");
	}

	public static void beginDraw() {
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		GlStateManager.disableTexture2D();
		GlStateManager.enableCull();
		GlStateManager.disableDepth();
		GlStateManager.pushMatrix();
	}

	public static void endDraw() {
		GlStateManager.popMatrix();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.enableDepth();
	}

	public static void setColor(int red, int green, int blue, int alpha) {
		GlStateManager.color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
	}

	public static void drawCircle(Entity entity) {
		final RenderManager renderManager = MC.getRenderManager();
		final float ticks = MC.getRenderPartialTicks();
		final double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks) - renderManager.viewerPosX;
		final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks) - renderManager.viewerPosY;
		final double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks) - renderManager.viewerPosZ;

		drawCircle(x, y + entity.height, z, entity.width * 0.8);
	}

	public static void drawCircle(double x, double y, double z, double radius) {
		BUILDER.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);

		for (int i = 0; i < CIRCLE_SEGMENTS; i++) {
			final double vx = x + radius * Math.cos(i * TWICE_PI / CIRCLE_SEGMENTS);
			final double vz = z + radius * Math.sin(i * TWICE_PI / CIRCLE_SEGMENTS);

			BUILDER.pos(vx, y, vz).endVertex();
		}

		TESSELLATOR.draw();
	}
}
