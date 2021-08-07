package io.github.gershon.dailyquests.quests.tasks.impl;

import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

public abstract class BaseItemTask extends Task {
    private String itemType;
    private boolean any;

    public BaseItemTask(String title, TaskType taskType, int amount) {
        super(title, taskType, amount);
        itemType = ItemTypes.DIAMOND_SWORD.getId();
    }

    public BaseItemTask(TaskType taskType) {
        super(taskType);
    }

    public ItemType getItemType() {
        return Sponge.getRegistry().getType(ItemType.class, itemType).get();
    }

    public boolean isAny() {
        return any;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }
}
