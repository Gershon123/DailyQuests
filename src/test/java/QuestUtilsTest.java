import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
    void createQuestListForPlayer() {
        Assertions.assertTrue(QuestUtils.createQuestListForPlayer(this.quests).get(0).getId().equals("123"));
        Assertions.assertNotNull(QuestUtils.createQuestListForPlayer(null));
        Assertions.assertTrue(QuestUtils.createQuestListForPlayer(null).size() == 0);
        quests.add(new RepeatableQuest("new quest 1"));
        quests.add(new RepeatableQuest("new quest 2"));
        quests.add(new RepeatableQuest("new quest 3"));
        Assertions.assertNotNull(QuestUtils.createQuestListForPlayer(this.quests));
        Assertions.assertTrue(QuestUtils.createQuestListForPlayer(this.quests).size() == 3);

    }
}