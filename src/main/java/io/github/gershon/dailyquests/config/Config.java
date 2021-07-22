package io.github.gershon.dailyquests.config;

import io.github.gershon.dailyquests.DailyQuests;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.asset.Asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static HashMap<String, Object> config = new HashMap<>();

    public static void setup(Path path, ConfigurationLoader<CommentedConfigurationNode> configManager) {
        if (Files.notExists(path.resolve("DailyQuests.conf")))
            install(path);
        load(configManager);

    }

    public static void load(ConfigurationLoader<CommentedConfigurationNode> configManager) {
        try {
            ConfigurationNode configNode = configManager.load();
            config.put("questComplete", configNode.getNode("questComplete").getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void install(Path path) {
        try {
            Asset configFile = DailyQuests.getInstance().getPluginContainer().getAsset("dailyquests.conf").get();
            configFile.copyToDirectory(path);
            DailyQuests.getInstance().getLogger().info("DailyQuests configuration installed with success.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Object> getConfig() {
        return config;
    }

}
