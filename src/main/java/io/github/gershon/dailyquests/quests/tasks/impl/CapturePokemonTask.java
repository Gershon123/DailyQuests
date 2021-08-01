package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CapturePokemonTask extends Task {

    private EnumSpecies species;
    private boolean any;

    public CapturePokemonTask(String title, int amount) {
        super(title, TaskType.CATCH_POKEMON, amount);
        this.species = EnumSpecies.Pikachu;
    }

    public CapturePokemonTask() {
        super(TaskType.CATCH_POKEMON);
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

    // Move this to the parent and pass it functions to check
    // Child will implement a valid test which is (capturePokemonTask.isAny() || capturePokemonTask.getSpecies() == species)
    /*
      Gets all of the appropriate quests with tasks related to the captured pokemon
    */
    public static List<Quest> getApplicableQuests(List<Quest> quests, EnumSpecies species) {
        return quests != null ? quests.stream().filter(quest -> {
            Task task = quest.getTask();
            if (task == null || task.getTaskType() != TaskType.CATCH_POKEMON) {
                return false;
            }

            CapturePokemonTask capturePokemonTask = (CapturePokemonTask) task;
            return capturePokemonTask != null && (capturePokemonTask.isAny() || capturePokemonTask.getSpecies() == species);
        }).collect(Collectors.toList()) : new ArrayList<>();
    }
}
