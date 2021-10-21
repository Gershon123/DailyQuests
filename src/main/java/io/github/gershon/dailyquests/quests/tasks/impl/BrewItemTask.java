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

public class BrewItemTask extends BaseItemTask {

    public BrewItemTask(String title, int amount) {
        super(title, TaskType.BREW_ITEM, amount);
        setItemType(ItemTypes.LINGERING_POTION.getId());
    }

    public BrewItemTask() {
        super(TaskType.BREW_ITEM);
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, ItemStack itemStack) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.BREW_ITEM) || itemStack == null || itemStack.isEmpty()) {
                return false;
            }

            BrewItemTask brewItemTask = (BrewItemTask) task;
            return brewItemTask != null && (brewItemTask.isAny() ||
                    itemStack.getType().getId().equals(brewItemTask.getItemType().getId()));
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
