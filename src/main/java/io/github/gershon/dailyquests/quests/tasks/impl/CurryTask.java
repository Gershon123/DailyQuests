package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumCurryKey;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurryTask extends Task {

    private EnumCurryKey curry;
    private boolean any;

    public CurryTask(String title, int amount) {
        super(title, TaskType.COOK_CURRY, amount);
        curry = EnumCurryKey.NONE;
    }

    public CurryTask() {
        super(TaskType.COOK_CURRY);
    }

    public boolean isAny() {
        return any;
    }

    public EnumCurryKey getCurry() {
        return curry;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public void setCurry(EnumCurryKey curry) {
        this.curry = curry;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumCurryKey curryKey) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.COOK_CURRY)) {
                return false;
            }

            CurryTask apricornTask = (CurryTask) task;
            return apricornTask != null && (apricornTask.isAny() || apricornTask.getCurry() == curryKey);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
