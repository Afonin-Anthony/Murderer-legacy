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

import java.util.Optional;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public final class Murderer {

	private static Murderer instance;

	private static final Minecraft MC = Minecraft.getMinecraft();

	private boolean enabled;

	private Optional<Entity> killer = Optional.empty();

	private Optional<Entity> detective = Optional.empty();

	public static Murderer instance() {
		if (instance == null) {
			instance = new Murderer();
		}

		return instance;
	}

	private Murderer() {

	}

	public void setEnabled(boolean enabled) {
		if (enabled) {
			MinecraftForge.EVENT_BUS.register(this);
		} else {
			MinecraftForge.EVENT_BUS.unregister(this);
		}

		this.enabled = enabled;
		reset();
		printStatus();
	}

	public boolean isEnabled() {
		return enabled;
	}

	private void printStatus() {
		final String status = "Murderer " + (enabled ? "\u00a7a\u00a7lenabled" : "\u00a7c\u00a7ldisabled");
		MC.player.sendStatusMessage(new TextComponentString(status), true);
	}

	private void forEachPlayer(Consumer<EntityPlayer> action) {
		for (Entity entity : MC.world.loadedEntityList) {
			if (entity instanceof EntityOtherPlayerMP) {
				action.accept((EntityOtherPlayerMP) entity);
			}
		}
	}

	private void reset() {
		killer = Optional.empty();
		detective = Optional.empty();
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		reset();
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (MC.world == null) {
			return;
		}

		forEachPlayer(player -> {
			final Item main = player.getHeldItemMainhand().getItem();
			final Item off = player.getHeldItemOffhand().getItem();

			if (main instanceof ItemSword || off instanceof ItemSword) {
				killer = Optional.of(player);
			} else if (main instanceof ItemBow || off instanceof ItemBow) {
				detective = Optional.of(player);
			}
		});

	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent evnent) {
		RenderingUtil.beginDraw();

		forEachPlayer(player -> {
			if (killer.isPresent() && player == killer.get()) {
				RenderingUtil.setColor(Settings.KillerColor.red, Settings.KillerColor.green, Settings.KillerColor.blue,
						Settings.KillerColor.alpha);
			} else if (detective.isPresent() && player == detective.get()) {
				RenderingUtil.setColor(Settings.DetectiveColor.red, Settings.DetectiveColor.green,
						Settings.DetectiveColor.blue, Settings.DetectiveColor.alpha);
			} else {
				RenderingUtil.setColor(Settings.InnocentsColor.red, Settings.InnocentsColor.green,
						Settings.InnocentsColor.blue, Settings.InnocentsColor.alpha);
			}

			RenderingUtil.drawCircle(player);
		});

		RenderingUtil.endDraw();
	}
}
