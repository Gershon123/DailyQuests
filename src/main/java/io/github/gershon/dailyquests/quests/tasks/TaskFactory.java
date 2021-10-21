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
            case FISHING_POKEMON:
                return new FishingPokemonTask(title, amount);
            case COOK_CURRY:
                return new CurryTask(title, amount);
            case SMELT_ITEM:
                return new SmeltItemTask(title, amount);
            case BREW_ITEM:
                return new BrewItemTask(title, amount);
            case BREAK_BLOCK:
                return new BreakBlockTask(title, amount);
            default:
                return null;
        }
    }
}
