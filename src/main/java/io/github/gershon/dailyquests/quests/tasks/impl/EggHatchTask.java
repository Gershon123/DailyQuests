package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EggHatchTask extends BasePokemonTask {

    public EggHatchTask(String title, int amount) {
        super(title, TaskType.HATCH_POKEMON, amount);
        setSpecies(EnumSpecies.Pikachu);
    }

    public EggHatchTask() {
        super(TaskType.HATCH_POKEMON);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumSpecies species) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.HATCH_POKEMON)) {
                return false;
            }
            EggHatchTask eggHatchTask = (EggHatchTask) task;

            if (eggHatchTask == null) {
                return false;
            }

            EnumType type = eggHatchTask.getType();
            if (type != null && species.getBaseStats().getTypeList().contains(type)) {
                return true;
            }

            return eggHatchTask != null && (eggHatchTask.isAny() || eggHatchTask.getSpecies() == species);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
