package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class QuestUtilsTest {

    ArrayList<Quest> quests;

    @BeforeEach
    void beforeEach() {
        quests = new ArrayList<>();
        quests.add(new RepeatableQuest("123"));
    }

    @Test
    void questExists() {
        Assertions.assertTrue(QuestUtils.questExists("123", this.quests));
        Assertions.assertFalse(QuestUtils.questExists("1234", this.quests));
        Assertions.assertFalse(QuestUtils.questExists("12", this.quests));
        Assertions.assertFalse(QuestUtils.questExists(null, this.quests));
        Assertions.assertFalse(QuestUtils.questExists(null, null));
    }

    @Test
    void createQuestProgressesForPlayer() {
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests).get("123").getQuestId(), "123");
        Assertions.assertNotNull(QuestPlayerUtils.getQuestProgressesFromQuests(null));
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(null).keySet().size(), 0);
        quests.add(new RepeatableQuest("new quest 1"));
        quests.add(new RepeatableQuest("new quest 2"));
        quests.add(new RepeatableQuest("new quest 3"));
        Assertions.assertNotNull(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests));
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests).get("new quest 1").getQuestId(), "new quest 1");
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests).get("new quest 2").getQuestId(), "new quest 2");
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests).get("new quest 3").getQuestId(), "new quest 3");
        Assertions.assertEquals(QuestPlayerUtils.getQuestProgressesFromQuests(this.quests).size(), 4);
    }

    @Test
    void getQuest() {
        Assertions.assertEquals(QuestUtils.getQuest(this.quests, "123").getId(), "123");
        Assertions.assertNull(QuestUtils.getQuest(null, "123"));
        quests.add(new RepeatableQuest("new quest 1"));
        quests.add(new RepeatableQuest("new quest 2"));
        quests.add(new RepeatableQuest("new quest 3"));
        Assertions.assertEquals(QuestUtils.getQuest(this.quests, "new quest 1").getId(), "new quest 1");
        Assertions.assertEquals(QuestUtils.getQuest(this.quests, "new quest 2").getId(), "new quest 2");
        Assertions.assertEquals(QuestUtils.getQuest(this.quests, "new quest 3").getId(), "new quest 3");
    }

    @Test
    void getNextValidPosition() {
        Assertions.assertEquals(QuestUtils.getNextValidPosition(this.quests), 1);
        quests.add(getQuestAtPosition(1));
        quests.add(getQuestAtPosition(3));
        Assertions.assertEquals(QuestUtils.getNextValidPosition(this.quests), 2);
    }

    private static Quest getQuestAtPosition(int position) {
        Quest quest = new RepeatableQuest("new quest  " + position);
        quest.setPosition(position);
        return quest;
    }
}