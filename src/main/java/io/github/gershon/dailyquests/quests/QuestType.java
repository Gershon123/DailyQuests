package io.github.gershon.dailyquests.quests;

import java.util.Arrays;

public enum QuestType {
    ONE_TIME,
    REPEATABLE;

    public static boolean isValidQuestType(String type) {
        return Arrays.stream(QuestType.values()).anyMatch(name -> name.toString().equals(type));
    }
}


