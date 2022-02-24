package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CapturePokemonTask extends BasePokemonTask {

    public CapturePokemonTask(String title, int amount) {
        super(title, TaskType.CATCH_POKEMON, amount);
        setSpecies(EnumSpecies.Pikachu);
    }

    public CapturePokemonTask() {
        super(TaskType.CATCH_POKEMON);
    }

    public static List<Quest> getApplicableQuests(List<Quest> quests, EntityPixelmon pixelmon) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.CATCH_POKEMON)) {
                return false;
            }

            CapturePokemonTask capturePokemonTask = (CapturePokemonTask) task;

            if (capturePokemonTask == null) {
                return false;
            }

            if (capturePokemonTask.isShiny() && !pixelmon.getPokemonData().isShiny()) {
                return false;
            }

            EnumType type = capturePokemonTask.getType();
            if (type != null && pixelmon.getPokemonData().getBaseStats().getTypeList().contains(type)) {
                return true;
            }
            return capturePokemonTask.isAny()
                    || capturePokemonTask.getSpecies() == pixelmon.getSpecies();
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
