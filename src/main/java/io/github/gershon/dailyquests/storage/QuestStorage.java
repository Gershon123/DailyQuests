package io.github.gershon.dailyquests.storage;

import com.google.gson.*;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.adapters.RuntimeTypeAdapterFactory;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class QuestStorage {
    private final File directory = new File("config/" + DailyQuests.ID + "/quests/");
    private RuntimeTypeAdapterFactory<Quest> questTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Quest.class, "questType")
            .registerSubtype(RepeatableQuest.class, "REPEATABLE");
    private RuntimeTypeAdapterFactory<Task> taskTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Task.class, "taskType")
            .registerSubtype(HarvestApricornTask.class, "HARVEST_APRICORN");
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(questTypeAdapterFactory)
            .registerTypeAdapterFactory(taskTypeAdapterFactory)
            .create();

    public void loadQuests(ArrayList<Quest> quests) {
        int questsLoaded = 0;
        try {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        quests.add(gson.fromJson(content, Quest.class));
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

    public void storeQuests(ArrayList<Quest> quests) {
        if (quests != null) {
            quests.forEach(quest -> {
                // IMPORTANT - THIS SHOULD BE EXTENSIVELY TESTED TO MAKE SURE SAVING / LOADING WORKS CORRECTLY
                String json = gson.toJson(quest, Quest.class);
                String dir = directory.getPath() + "/";
                String fileName = quest.getId() + ".json";
                io.github.gershon.dailyquests.utils.FileUtils.writeToFile(dir, fileName, json);
            });
        }
    }

}