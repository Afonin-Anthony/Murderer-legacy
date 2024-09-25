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

import java.util.*;

import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public final class CommandHandler extends CommandBase {

	private static final String NAME = "murderer";

	private static final String USAGE = "murderer <enable|disable|toggle>";

	private static final String ENABLE_ACTION = "enable";

	private static final String DISABLE_ACTION = "disable";

	private static final String TOGGLE_ACTION = "toggle";

	private static final Murderer MURDERER = Murderer.instance();

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return USAGE;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 1) {
			throw new CommandException("\u00a74\u00a7lMurderer:\u00a7c Wrong usage");
		}

		final String action = args[0];

		if (action.equalsIgnoreCase(ENABLE_ACTION)) {
			MURDERER.setEnabled(true);
		} else if (action.equalsIgnoreCase(DISABLE_ACTION)) {
			MURDERER.setEnabled(false);
		} else if (action.equalsIgnoreCase(TOGGLE_ACTION)) {
			MURDERER.setEnabled(!MURDERER.isEnabled());
		} else {
			throw new CommandException("\u00a74\u00a7lMurderer:\u00a7c Unknown action \"" + action + "\"");
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return Arrays.asList(ENABLE_ACTION, DISABLE_ACTION, TOGGLE_ACTION);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
