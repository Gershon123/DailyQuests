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

public class BreakBlockTask extends BaseItemTask {

    public BreakBlockTask(String title, int amount) {
        super(title, TaskType.BREAK_BLOCK, amount);
        setItemType(ItemTypes.DIAMOND.getId());
    }

    public BreakBlockTask() {
        super(TaskType.BREAK_BLOCK);
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, ItemType itemType) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.BREAK_BLOCK) || itemType == null) {
                return false;
            }

            BreakBlockTask breakBlockTask = (BreakBlockTask) task;
            return breakBlockTask != null && (breakBlockTask.isAny() ||
                    itemType.getId().equals(breakBlockTask.getItemType().getId()));
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
