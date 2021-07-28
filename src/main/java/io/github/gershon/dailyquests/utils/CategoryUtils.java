package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.categories.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {

    public static boolean categoryExists(String id, ArrayList<Category> categories) {
        return categories != null ? categories.stream().anyMatch(category -> category.getId().equals(id)) : false;
    }

    public static int getNextValidPosition(ArrayList<Category> categoryList) {
        int position = 0;

        if (categoryList == null) {
            return 0;
        }

        for (int x = 0; x < categoryList.size() - 1; x++) {
            position++;
            if (position != categoryList.get(x + 1).getPosition()) {
                return position;
            }
        }

        return position;
    }

    public static ArrayList<Quest> getQuestsInCategory(Category category, List<Quest> questList) {
        ArrayList<Quest> quests = new ArrayList<>();
        if (category == null || questList == null) {
            return quests;
        }

        questList.forEach(quest -> {
            String categoryId = quest.getCategoryId();
            if (categoryId != null && categoryId.equals(category.getId())) {
                quests.add(quest);
            }
        });

        return quests;
    }
}
