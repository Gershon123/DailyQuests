package io.github.gershon.dailyquests.quests.categories;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.utils.CategoryUtils;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

public class Category {
    private String id;
    private String title;
    private ItemType itemType;
    private int position;

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
        this.position = CategoryUtils.getNextValidPosition(DailyQuests.getInstance().getCategories());
        this.itemType = ItemTypes.PAPER;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public ItemType itemType() {
        return itemType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoint(int position) {
        this.position = position;
    }
}
