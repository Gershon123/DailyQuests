package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattlePokemonTask extends Task {

    private EnumSpecies species;
    private boolean any;

    public BattlePokemonTask(String title, int amount) {
        super(title, TaskType.DEFEAT_POKEMON, amount);
        this.species = EnumSpecies.Pikachu;
    }

    public BattlePokemonTask() {
        super(TaskType.DEFEAT_POKEMON);
    }

    public EnumSpecies getSpecies() {
        return species;
    }

    public boolean isAny() {
        return this.any;
    }

    public void setSpecies(EnumSpecies species) {
        this.species = species;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }

    /*
      Gets all of the appropriate quests with tasks related to the battled type
    */
    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumSpecies species) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (task == null || task.getTaskType() != TaskType.DEFEAT_POKEMON) {
                return false;
            }

            BattlePokemonTask capturePokemonTask = (BattlePokemonTask) task;
            return capturePokemonTask != null && (capturePokemonTask.isAny() || capturePokemonTask.getSpecies() == species);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
