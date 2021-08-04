package io.github.gershon.dailyquests.quests.tasks;

import io.github.gershon.dailyquests.quests.tasks.impl.*;

public class TaskFactory {

    public static Task createTask(String title, TaskType taskType, int amount) {
        switch (taskType) {
            case HARVEST_APRICORN:
                return new HarvestApricornTask(title, amount);
            case CATCH_POKEMON:
                return new CapturePokemonTask(title, amount);
            case DEFEAT_POKEMON:
                return new BattlePokemonTask(title, amount);
            case CRAFT_ITEM:
                return new CraftItemTask(title, amount);
            case HATCH_POKEMON:
                return new EggHatchTask(title, amount);
            default:
                return null;
        }
    }
}
