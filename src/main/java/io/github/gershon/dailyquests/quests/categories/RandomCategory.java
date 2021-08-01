package io.github.gershon.dailyquests.quests.categories;

import java.util.ArrayList;
import java.util.List;

public class RandomCategory extends Category {

    private List<String> randomQuests;
    private int maxRandomQuests;
    private transient CategoryType categoryType = CategoryType.RANDOM;

    public RandomCategory(String id, String title) {
        super(id, title);
        this.randomQuests = new ArrayList<>();
        this.maxRandomQuests = 3;
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

    public int getMaxRandomQuests() {
        return maxRandomQuests;
    }

    public void setMaxRandomQuests(int maxRandomQuests) {
        this.maxRandomQuests = maxRandomQuests;
    }

    public void setRandomQuests(List<String> randomQuests) {
        this.randomQuests = randomQuests;
    }
}
