package io.github.gershon.dailyquests.quests.tasks.impl;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftItemTask extends BaseItemTask {

    public CraftItemTask(String title, int amount) {
        super(title, TaskType.CRAFT_ITEM, amount);
        setItemType(ItemTypes.DIAMOND_SWORD.getId());
    }

    public CraftItemTask() {
        super(TaskType.CRAFT_ITEM);
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, ItemType itemType) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.CRAFT_ITEM)) {
                return false;
            }

            CraftItemTask craftItemTask = (CraftItemTask) task;
            return craftItemTask != null && (craftItemTask.isAny() ||
                    craftItemTask.getItemType().getId().equals(itemType.getId()));
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
