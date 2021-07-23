package io.github.gershon.dailyquests.quests.tasks;

import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;

public class TaskFactory {


    public static Task createTask(String title, TaskType taskType, int amount) {
        switch (taskType) {
            case HARVEST_APRICORN:
                return new HarvestApricornTask(title, taskType, amount);
            default:
                return null;
        }
    }
}
