package io.github.gershon.dailyquests.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class QuestPlayerStorage {
    private final File directory = new File("config/" + DailyQuests.ID + "/players/");
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public void loadPlayer(UUID uuid) {
        File file = new File(directory.getPath() + "/" + uuid.toString() + ".json");
        QuestPlayer questPlayer;
        if (!file.isFile()) {
            questPlayer = new QuestPlayer(uuid);
            DailyQuests.getInstance().playerMap.put(questPlayer.getPlayerId(), questPlayer);
            return;
        }
        try {
            String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            questPlayer = gson.fromJson(content, QuestPlayer.class);
            DailyQuests.getInstance().playerMap.put(questPlayer.getPlayerId(), questPlayer);
        } catch (Exception e) {
            DailyQuests.getInstance().getLogger().error("Failed to import " + file.getName());
            DailyQuests.getInstance().getLogger().error("Error: " + e.getMessage());
        }
    }

    public void savePlayer(QuestPlayer questPlayer) {
        // IMPORTANT - THIS SHOULD BE EXTENSIVELY TESTED TO MAKE SURE SAVING / LOADING WORKS CORRECTLY
        String json = gson.toJson(questPlayer, QuestPlayer.class);
        String dir = directory.getPath() + "/";
        String fileName = questPlayer.getPlayerId().toString() + ".json";
        io.github.gershon.dailyquests.utils.FileUtils.writeToFile(dir, fileName, json);
    }
}
