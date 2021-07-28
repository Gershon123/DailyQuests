package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.quests.categories.Category;

import java.util.ArrayList;

public class CategoryUtils {

    public static boolean categoryExists(String id, ArrayList<Category> categories) {
        return categories != null ? categories.stream().anyMatch(category -> category.getId().equals(id)) : false;
    }

    public static int getNextValidPosition(ArrayList<Category> categoryList) {
        int position = 0;

        if (categoryList == null) {
            return 0;
        }

        for (Category category : categoryList) {
            position++;
        }

        return position;

    }
}
