package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.items.EnumApricorns;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HarvestApricornTask extends Task {

    private EnumApricorns apricorn;
    private boolean harvestAny;

    public HarvestApricornTask(String title, TaskType taskType, int amount) {
        super(title, taskType, amount);
        apricorn = EnumApricorns.Black;
    }

    public HarvestApricornTask() {
        super(TaskType.HARVEST_APRICORN);
    }

    public EnumApricorns getApricorn() {
        return apricorn;
    }

    public boolean isHarvestAny() {
        return harvestAny;
    }

    public void setApricorn(EnumApricorns apricorn) {
        this.apricorn = apricorn;
    }

    public void setHarvestAny(boolean harvestAny) {
        this.harvestAny = harvestAny;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    /*
      Gets all of the appropriate quests with tasks related to the broken apricorn type
    */
    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumApricorns harvestedApricorn) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (task == null || task.getTaskType() != TaskType.HARVEST_APRICORN) {
                return false;
            }

            HarvestApricornTask apricornTask = (HarvestApricornTask) task;
            return apricornTask != null ? apricornTask.isHarvestAny() || apricornTask.getApricorn() == harvestedApricorn : false;
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
