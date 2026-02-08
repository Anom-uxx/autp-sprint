package com.anomuxx;

import net.fabricmc.api.ClientModInitializer;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class AutosprintClient implements ClientModInitializer {
	private static final String KEY_TOGGLE = "key.auto_sprint.toggle";
	private static final int MIN_FOOD_LEVEL_TO_SPRINT = 7;

	private KeyMapping toggleKey;
	private AutosprintConfig config;

	@Override
	public void onInitializeClient() {
		config = AutosprintConfig.load();
		toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				KEY_TOGGLE,
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_Z,
				KeyMapping.Category.MISC
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleKey.consumeClick()) {
				config.setEnabled(!config.isEnabled());
				config.save();
				showStatus(client);
			}

			applyAutoSprint(client);
		});
	}

	private void showStatus(Minecraft client) {
		if (client.player == null) {
			return;
		}
		String state = config.isEnabled() ? "AN" : "AUS";
		client.player.displayClientMessage(Component.literal("Auto-sprint: " + state), true);
	}

	private void applyAutoSprint(Minecraft client) {
		if (!config.isEnabled()) {
			return;
		}
		if (client.player == null) {
			return;
		}

		client.options.keySprint.setDown(false);

		if (client.player.getFoodData().getFoodLevel() < MIN_FOOD_LEVEL_TO_SPRINT) {
			client.player.setSprinting(false);
			return;
		}
		if (client.options.keyDown.isDown()
				|| client.options.keyLeft.isDown()
				|| client.options.keyRight.isDown()) {
			client.player.setSprinting(false);
			return;
		}
		if (client.options.keyShift.isDown()) {
			client.player.setSprinting(false);
			return;
		}
		if (!client.options.keyUp.isDown()) {
			client.player.setSprinting(false);
			return;
		}
		client.player.setSprinting(true);
	}
}