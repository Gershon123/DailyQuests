package io.github.gershon.dailyquests.quests.tasks.impl;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmeltItemTask extends BaseItemTask {

    public SmeltItemTask(String title, int amount) {
        super(title, TaskType.SMELT_ITEM, amount);
        setItemType(ItemTypes.GOLD_INGOT.getId());
    }

    public SmeltItemTask() {
        super(TaskType.SMELT_ITEM);
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, ItemStack itemStack) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.SMELT_ITEM) || itemStack == null || itemStack.isEmpty()) {
                return false;
            }

            SmeltItemTask craftItemTask = (SmeltItemTask) task;
            return craftItemTask != null && (craftItemTask.isAny() ||
                    itemStack.getType().getId().equals(craftItemTask.getItemType().getId()));
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
