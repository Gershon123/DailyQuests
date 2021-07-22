package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.tasks.TaskType;

import java.util.Arrays;

public class TaskUtils {

    public static boolean isValidTask(String task) {
        return Arrays.stream(TaskType.values()).anyMatch(name -> name.toString().equals(task));
    }
}
