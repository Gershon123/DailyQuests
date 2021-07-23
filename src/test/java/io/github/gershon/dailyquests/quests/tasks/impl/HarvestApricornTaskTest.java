package io.github.gershon.dailyquests.quests.tasks.impl;

import com.pixelmonmod.pixelmon.enums.items.EnumApricorns;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class HarvestApricornTaskTest {

    ArrayList<Quest> quests;

    @BeforeEach
    void beforeEach() {
        quests = new ArrayList<>();
        quests.add(QuestUtils.createRepeatableQuest("test1", "", TaskType.BREAK_BLOCK));
        // Default is black apricorn, with harvest any turned off
        quests.add(QuestUtils.createRepeatableQuest("test2", "", TaskType.HARVEST_APRICORN));
        quests.add(QuestUtils.createRepeatableQuest("test3", "", TaskType.CATCH_POKEMON));
    }

    @Test
    void getApplicableQuests() {
        // We should expect to find none when a red apricorn is broken
        Assertions.assertTrue(HarvestApricornTask.getApplicableQuests(quests, EnumApricorns.Red).size() == 0);
        // We should expect to find one quest when a black apricorn is broken
        Assertions.assertTrue(HarvestApricornTask.getApplicableQuests(quests, EnumApricorns.Black).size() == 1);
        // We should handle nulls
        Assertions.assertTrue(HarvestApricornTask.getApplicableQuests(null, EnumApricorns.Red).size() == 0);

        quests.add(QuestUtils.createRepeatableQuest("test4", "", TaskType.HARVEST_APRICORN));
        HarvestApricornTask task = (HarvestApricornTask) quests.get(3).getTask();
        task.setApricorn(EnumApricorns.Pink);
        task.setHarvestAny(true);

        // Broke a black apricorn, the task is for pink apricorns but since harvest any is on it will count
        Assertions.assertTrue(HarvestApricornTask.getApplicableQuests(quests, EnumApricorns.Black).size() == 2);
    }

}