package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattlePokemonTask extends BasePokemonTask {

    public BattlePokemonTask(String title, int amount) {
        super(title, TaskType.DEFEAT_POKEMON, amount);
        setSpecies(EnumSpecies.Pikachu);
    }

    public BattlePokemonTask() {
        super(TaskType.DEFEAT_POKEMON);
    }

    /*
      Gets all of the appropriate quests with tasks related to the battled type
    */
    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumSpecies species) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (!Task.applicableTask(task, TaskType.DEFEAT_POKEMON)) {
                return false;
            }

            BattlePokemonTask capturePokemonTask = (BattlePokemonTask) task;
            return capturePokemonTask != null && (capturePokemonTask.isAny() || capturePokemonTask.getSpecies() == species);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}