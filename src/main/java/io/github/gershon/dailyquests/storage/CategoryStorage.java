package io.github.gershon.dailyquests.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.categories.Category;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class CategoryStorage {
    private final File directory = new File("config/" + DailyQuests.ID + "/categories/");
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public void loadCategories() {
        int categoriesLoaded = 0;
        try {
            File[] files = directory.listFiles();

            for (File file : files) {
                if (file.isFile()) {
                    try {
                        String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                        Category category = gson.fromJson(content, Category.class);
                        DailyQuests.getInstance().categories.put(category.getId(), category);
                        categoriesLoaded++;
                    } catch (Exception e) {
                        DailyQuests.getInstance().getLogger().error("Failed to import " + file.getName());
                        DailyQuests.getInstance().getLogger().error("Error: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            DailyQuests.getInstance().getLogger().error(e.getMessage());
        }
        DailyQuests.getInstance().getLogger().info("Loaded " + categoriesLoaded + " categories");
    }

    public void saveCategory(Category category) {
        // IMPORTANT - THIS SHOULD BE EXTENSIVELY TESTED TO MAKE SURE SAVING / LOADING WORKS CORRECTLY
        String json = gson.toJson(category, Category.class);
        String dir = directory.getPath() + "/";
        String fileName = category.getId().toString() + ".json";
        io.github.gershon.dailyquests.utils.FileUtils.writeToFile(dir, fileName, json);
        DailyQuests.getInstance().categories.put(category.getId(), category);
    }
}
