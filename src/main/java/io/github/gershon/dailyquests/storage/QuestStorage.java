package io.github.gershon.dailyquests.storage;

import com.google.gson.*;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.adapters.RuntimeTypeAdapterFactory;
import io.github.gershon.dailyquests.quests.OneTimeQuest;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.rewards.CommandReward;
import io.github.gershon.dailyquests.quests.rewards.ItemReward;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.quests.tasks.impl.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QuestStorage {
    private final File directory = new File("config/" + DailyQuests.ID + "/quests/");
    private RuntimeTypeAdapterFactory<Quest> questTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Quest.class, "questType")
            .registerSubtype(RepeatableQuest.class, "REPEATABLE")
            .registerSubtype(OneTimeQuest.class, "ONE_TIME");
    private RuntimeTypeAdapterFactory<Task> taskTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Task.class, "taskType")
            .registerSubtype(HarvestApricornTask.class, "HARVEST_APRICORN")
            .registerSubtype(BattlePokemonTask.class, "DEFEAT_POKEMON")
            .registerSubtype(CraftItemTask.class, "CRAFT_ITEM")
            .registerSubtype(SmeltItemTask.class, "SMELT_ITEM")
            .registerSubtype(BrewItemTask.class, "BREW_ITEM")
            .registerSubtype(BreakBlockTask.class, "BREAK_BLOCK")
            .registerSubtype(CapturePokemonTask.class, "CATCH_POKEMON")
            .registerSubtype(FishingPokemonTask.class, "FISHING_POKEMON")
            .registerSubtype(CurryTask.class, "COOK_CURRY")
            .registerSubtype(EggHatchTask.class, "HATCH_POKEMON");
    private RuntimeTypeAdapterFactory<Reward> rewardTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Reward.class, "rewardType")
            .registerSubtype(ItemReward.class, "ITEM")
            .registerSubtype(CommandReward.class, "COMMAND");
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(questTypeAdapterFactory)
            .registerTypeAdapterFactory(taskTypeAdapterFactory)
            .registerTypeAdapterFactory(rewardTypeAdapterFactory)
            .create();

    public void loadQuests() {
        int questsLoaded = 0;
        try {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        Quest quest = gson.fromJson(content, Quest.class);
                        DailyQuests.getInstance().quests.put(quest.getId(), quest);
                        questsLoaded++;
                    } catch (Exception e) {
                        DailyQuests.getInstance().getLogger().error("Failed to import " + file.getName());
                        DailyQuests.getInstance().getLogger().error("Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            DailyQuests.getInstance().getLogger().error(e.getMessage());
        }
        DailyQuests.getInstance().getLogger().info("Loaded " + questsLoaded + " quests");
    }

    public void storeQuest(Quest quest) {
        // IMPORTANT - THIS SHOULD BE EXTENSIVELY TESTED TO MAKE SURE SAVING / LOADING WORKS CORRECTLY
        String json = gson.toJson(quest, Quest.class);
        String dir = directory.getPath() + "/";
        String fileName = quest.getId() + ".json";
        io.github.gershon.dailyquests.utils.FileUtils.writeToFile(dir, fileName, json);
    }

}