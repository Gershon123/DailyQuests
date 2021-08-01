package io.github.gershon.dailyquests;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import io.github.gershon.dailyquests.commands.BaseCommand;
import io.github.gershon.dailyquests.config.Config;
import io.github.gershon.dailyquests.listeners.*;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.storage.CategoryStorage;
import io.github.gershon.dailyquests.storage.QuestPlayerStorage;
import io.github.gershon.dailyquests.storage.QuestStorage;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.sql.SqlService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Plugin(name = DailyQuests.NAME,
        id = DailyQuests.ID,
        version = DailyQuests.VERSION,
        authors = DailyQuests.AUTHOR,
        description = DailyQuests.DESC,
        dependencies = {@Dependency(id = "pixelmon")})
public class DailyQuests {

    public static final String NAME = "DailyQuests";
    public static final String ID = "dailyquests";
    public static final String VERSION = "1.0.0";
    public static final String AUTHOR = "Gershon";
    public static final String DESC = "Repeatable & one time quests";

    public final Map<UUID, QuestPlayer> playerMap = new HashMap<UUID, QuestPlayer>();
    public Map<String, Category> categories = new HashMap<String, Category>();
    public Map<String, Quest> quests = new HashMap<String, Quest>();
    public final QuestStorage questStorage = new QuestStorage();
    public final CategoryStorage categoryStorage = new CategoryStorage();

    @Inject
    private Logger logger;

    @Inject
    public Game game;

    @Inject
    private PluginContainer pluginContainer;
    private static DailyQuests instance;

    private ConfigurationLoader<CommentedConfigurationNode> configManager;
    @Inject
    @ConfigDir(sharedRoot = true)
    public Path configDir;

    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;

    public File getDefConfig() {
        return this.defaultConfig;
    }

    private EconomyService economyService;
    private HashSet<UUID> toggle;
    private boolean foundEconomyPlugin = false;
    private ScriptEngine scriptEngine;

    public static DailyQuests getInstance() {
        return instance;
    }

    private static QuestPlayerStorage questPlayerStorage;

    public static QuestPlayerStorage getQuestPlayerStorage() {
        return questPlayerStorage;
    }

    private String dbPath;
    private DataSource dataSource;

    public Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Listener
    public void onServerStart(GameInitializationEvent e) {
        instance = this;
        configManager = HoconConfigurationLoader.builder().setPath(configDir.resolve("dailyquests.conf")).build();
        loadItems();
        questPlayerStorage = new QuestPlayerStorage();

        try {
            this.dbPath = String.format("jdbc:h2:%s/players.db;mode=MySQL", getDefConfig().getParentFile().getAbsolutePath());
            this.dataSource = Sponge.getServiceManager().provide(SqlService.class).get().getDataSource(dbPath);
        } catch (Exception exception) {
            logger.error("Error loading plugin " + exception.getMessage());
        }
        Config.setup(configDir, configManager);
        logger.info("Registering listeners...");
        registerListeners();
        logger.info("Registering commands...");
        registerCommands();
        logger.info("Successfully initializated!");
        toggle = new HashSet<>();
        scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

    }

    public void loadItems() {
        categoryStorage.loadCategories();
        questStorage.loadQuests();
    }

    @Listener
    public void onServerStarted(GameStartedServerEvent e) {
        if (!foundEconomyPlugin) {
            Sponge.getEventManager().unregisterPluginListeners(this);
            for (CommandMapping commandMapping : Sponge.getCommandManager().getOwnedBy(this)) {
                Sponge.getCommandManager().removeMapping(commandMapping);
            }
            logger.error("DailyQuests didn't found a valid economy plugin. Unloading...");
        } else {
            updateQuestPlayers();
        }

    }

    @Listener
    public void onServerClose(GameStoppingServerEvent e) {
        logger.info("Saving all players.");
        Sponge.getServer().getOnlinePlayers().forEach(player -> {
            QuestPlayer questPlayer = playerMap.get(player.getUniqueId());
            if (questPlayer != null) {
                questPlayerStorage.savePlayer(questPlayer);
            }
        });
        logger.info("Plugin stopped.");
    }

    @Listener
    public void onGameReload(GameReloadEvent e) {
        Config.setup(configDir, configManager);
    }

    public void addQuest(Quest quest) {
        quests.put(quest.getId(), quest);
        questStorage.storeQuest(quest);
    }

    private void registerCommands() {
        Sponge.getCommandManager().register(this, new BaseCommand().getCommandSpec(), "quests", "dailyquests");
    }

    private void registerListeners() {
        game.getEventManager().registerListeners(this, new PlayerJoinListener());
        game.getEventManager().registerListeners(this, new CraftItemListener());
        Pixelmon.EVENT_BUS.register(new BeatWildPixelmonListener());
        Pixelmon.EVENT_BUS.register(new CaptureListener());
        Pixelmon.EVENT_BUS.register(new HarvestApricornListener());
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
        return this.configManager;
    }

    public EconomyService getEconomyService() {
        return this.economyService;
    }

    public PluginContainer getPluginContainer() {
        return this.pluginContainer;
    }

    public ArrayList<Quest> getQuests() {
        ArrayList<Quest> questList = new ArrayList<>();
        for (String key : quests.keySet()) {
            questList.add(quests.get(key));
        }
        return questList;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();
        for (String key : categories.keySet()) {
            categoryList.add(categories.get(key));
        }
        return categoryList;
    }

    public void setQuests(ArrayList<Quest> questList) {
        if (questList == null) {
            return;
        }

        questList.forEach(quest -> {
            quests.put(quest.getId(), quest);
        });
    }

    public void updateQuests(ArrayList<Quest> quests) {
        if (quests != null) {
            for (Quest quest : quests) {
                this.quests.put(quest.getId(), quest);
                questStorage.storeQuest(quest);
            }
        }
    }

    public void updateQuestPlayers() {
        Task.builder().execute(() -> {
            for (QuestPlayer questPlayer : playerMap.values()) {
                questPlayer.update();
            }
        }).async().interval(60L, TimeUnit.SECONDS).submit(DailyQuests.getInstance());
    }

    @Listener
    public void onChangeServiceProvider(ChangeServiceProviderEvent event) {
        if (event.getService().equals(EconomyService.class)) {
            economyService = (EconomyService) event.getNewProviderRegistration().getProvider();
            foundEconomyPlugin = true;
        }
    }

    public Logger getLogger() {
        return logger;
    }

    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }
}
