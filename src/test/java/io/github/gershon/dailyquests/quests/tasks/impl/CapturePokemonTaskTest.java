package io.github.gershon.dailyquests.quests.tasks.impl;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

class CapturePokemonTaskTest {

    ArrayList<Quest> quests;

    @BeforeEach
    void beforeEach() {
        quests = new ArrayList<>();
        quests.add(QuestUtils.createQuest("test1", "", QuestType.REPEATABLE, TaskType.BREAK_BLOCK));
        quests.add(QuestUtils.createQuest("test2", "", QuestType.REPEATABLE, TaskType.HARVEST_APRICORN));
        quests.add(QuestUtils.createQuest("test3", "", QuestType.REPEATABLE, TaskType.CATCH_POKEMON));
    }
}