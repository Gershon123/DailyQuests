package io.github.gershon.dailyquests.storage;

import com.google.gson.*;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.adapters.RuntimeTypeAdapterFactory;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Database {
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

    public void loadDatabase(ArrayList<Quest> quests) {
        DailyQuests.getInstance().getLogger().info("Loading Database...");
        try {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        DailyQuests.getInstance().getLogger().info("Trying to import " + file.getName());
                        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        quests.add(gson.fromJson(content, Quest.class));
                    } catch (Exception e) {
                        DailyQuests.getInstance().getLogger().error("Failed to import " + file.getName());
                        DailyQuests.getInstance().getLogger().error("Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            DailyQuests.getInstance().getLogger().error(e.getMessage());
        }
    }

    public void storeQuests(ArrayList<Quest> quests) {
        if (quests != null) {
            quests.forEach(quest -> {
                String json = gson.toJson(quest, Quest.class);
                String fileName = directory.getPath() + "/" + quest.getId() + ".json";
                writeToFile(fileName, json);
            });
        }
    }

    public void closeDatabase() {
        //db.close();
    }

    private void writeToFile(String fileName, String json) {
        try {
            File questFile = new File(fileName);
            questFile.createNewFile();
            FileWriter writer = new FileWriter(fileName);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}