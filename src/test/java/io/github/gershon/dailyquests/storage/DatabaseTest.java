package io.github.gershon.dailyquests.storage;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DatabaseTest {

    ArrayList<Quest> quests;
    Database database;

    @BeforeEach
    void beforeEach() {
        quests = new ArrayList<>();
        quests.add(QuestUtils.createRepeatableQuest("test1", "", TaskType.BREAK_BLOCK));
        quests.add(QuestUtils.createRepeatableQuest("test2", "", TaskType.HARVEST_APRICORN));
        quests.add(QuestUtils.createRepeatableQuest("test3", "", TaskType.CATCH_POKEMON));
        database = new Database();
    }

    @Test
    void storeQuests() {
        //database.storeQuests(quests);
    }

}