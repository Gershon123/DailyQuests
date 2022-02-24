package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FishingPokemonTask extends BasePokemonTask {

    public FishingPokemonTask(String title, int amount) {
        super(title, TaskType.FISHING_POKEMON, amount);
        setSpecies(EnumSpecies.Pikachu);
    }

    public FishingPokemonTask() {
        super(TaskType.FISHING_POKEMON);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumSpecies species) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.FISHING_POKEMON)) {
                return false;
            }

            FishingPokemonTask capturePokemonTask = (FishingPokemonTask) task;

            if (capturePokemonTask == null) {
                return false;
            }

            EnumType type = capturePokemonTask.getType();
            if (type != null && species.getBaseStats().getTypeList().contains(type)) {
                return true;
            }
            return capturePokemonTask.isAny() || capturePokemonTask.getSpecies() == species;
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
