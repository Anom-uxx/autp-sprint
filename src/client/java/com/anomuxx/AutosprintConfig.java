package com.anomuxx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutosprintConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final String CONFIG_FILE_NAME = "auto-sprint.json";

	private boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void save() {
		Path path = getConfigPath();
		try (Writer writer = Files.newBufferedWriter(path)) {
			GSON.toJson(this, writer);
		} catch (IOException e) {
			Autosprint.LOGGER.warn("Failed to save config to {}", path, e);
		}
	}

	public static AutosprintConfig load() {
		Path path = getConfigPath();
		if (Files.exists(path)) {
			try (Reader reader = Files.newBufferedReader(path)) {
				AutosprintConfig config = GSON.fromJson(reader, AutosprintConfig.class);
				if (config != null) {
					return config;
				}
			} catch (IOException e) {
				Autosprint.LOGGER.warn("Failed to read config from {}", path, e);
			}
		}

		AutosprintConfig config = new AutosprintConfig();
		config.save();
		return config;
	}

	private static Path getConfigPath() {
		return FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME);
	}
}
