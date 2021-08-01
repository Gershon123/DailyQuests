package io.github.gershon.dailyquests.quests.tasks.impl;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftItemTask extends Task {

    private String itemType;
    private boolean any;

    public CraftItemTask(String title, int amount) {
        super(title, TaskType.CRAFT_ITEM, amount);
        itemType = ItemTypes.DIAMOND_SWORD.getId();
    }

    public CraftItemTask() {
        super(TaskType.CRAFT_ITEM);
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

    /*
      Gets all of the appropriate quests with tasks related to the craft item type
    */
    public static List<Quest> getApplicableQuests(List<Quest> quests, ItemType itemType) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (task == null || task.getTaskType() != TaskType.CRAFT_ITEM) {
                return false;
            }

            CraftItemTask craftItemTask = (CraftItemTask) task;
            return craftItemTask != null && (craftItemTask.isAny() ||
                    craftItemTask.getItemType().getId().equals(itemType.getId()));
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
