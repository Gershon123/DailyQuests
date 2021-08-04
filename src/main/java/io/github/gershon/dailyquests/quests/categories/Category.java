package io.github.gershon.dailyquests.quests.categories;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.utils.CategoryUtils;
import io.github.gershon.dailyquests.utils.TextUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Category {
    private String id;
    private String title;
    private String itemType;
    private int position;
    private ArrayList<String> lore;
    private transient CategoryType categoryType = CategoryType.NORMAL;

    public Category(String id, String title) {
        this.id = id;
        this.title = title;
        this.position = CategoryUtils.getNextValidPosition(DailyQuests.getInstance().getCategories());
        this.itemType = ItemTypes.PAPER.getId();
        this.lore = new ArrayList<>();
    }

    public Category(String id) {
        this.id = id;
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
        return Sponge.getRegistry().getType(ItemType.class, itemType).get();
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public List<Text> getLore() {
        return lore.stream().map(lore -> TextUtils.getText(lore)).collect(Collectors.toList());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }
}
