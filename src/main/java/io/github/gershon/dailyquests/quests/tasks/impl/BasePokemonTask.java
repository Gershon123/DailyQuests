package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.EnumType;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

public abstract class BasePokemonTask extends Task {
    private EnumSpecies species;
    private EnumType type;
    private boolean any;
    private boolean shiny;

    public BasePokemonTask(String title, TaskType taskType, int amount) {
        super(title, taskType, amount);
        this.species = EnumSpecies.Pikachu;
    }

    public BasePokemonTask(TaskType taskType) {
        super(taskType);
    }

    public EnumSpecies getSpecies() {
        return species;
    }

    public EnumType getType() {
        return type;
    }

    public boolean isAny() {
        return this.any;
    }

    public boolean isShiny() {
        return this.shiny;
    }

    public void setSpecies(EnumSpecies species) {
        this.species = species;
    }

    public void setType(EnumType type) {
        this.type = type;
    }

    public void setAny(boolean any) {
        this.any = any;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    @Override
    public void completeTask(Player player) {
        super.completeTask(player);
    }
}
