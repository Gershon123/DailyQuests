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
    private boolean any;

    public HarvestApricornTask(String title, int amount) {
        super(title, TaskType.HARVEST_APRICORN, amount);
        apricorn = EnumApricorns.Black;
    }

    public HarvestApricornTask() {
        super(TaskType.HARVEST_APRICORN);
    }

    public EnumApricorns getApricorn() {
        return apricorn;
    }

    public boolean isAny() {
        return any;
    }

    public void setApricorn(EnumApricorns apricorn) {
        this.apricorn = apricorn;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumApricorns harvestedApricorn) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.HARVEST_APRICORN)) {
                return false;
            }

            HarvestApricornTask apricornTask = (HarvestApricornTask) task;
            return apricornTask != null && (apricornTask.isAny() || apricornTask.getApricorn() == harvestedApricorn);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
