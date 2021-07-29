package io.github.gershon.dailyquests.quests.categories;

import java.util.ArrayList;
import java.util.List;

public class RandomCategory extends Category {

    List<String> randomQuests;
    private transient CategoryType categoryType = CategoryType.RANDOM;

    public RandomCategory(String id, String title) {
        super(id, title);
        this.randomQuests = new ArrayList<>();
    }

    public RandomCategory(String id) {
        super(id);
        this.randomQuests = new ArrayList<>();
    }

    public List<String> getRandomQuests() {
        return randomQuests;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setRandomQuests(List<String> randomQuests) {
        this.randomQuests = randomQuests;
    }
}
