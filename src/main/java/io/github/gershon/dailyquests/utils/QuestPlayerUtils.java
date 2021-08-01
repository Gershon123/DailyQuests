package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import org.spongepowered.api.entity.living.player.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestPlayerUtils {

    public static QuestPlayer createQuestPlayer(User user) {
        QuestPlayer questPlayer = new QuestPlayer(user.getUniqueId());
        questPlayer.setQuestProgressMap(createQuestProgressesForPlayer(DailyQuests.getInstance().getQuests()));
        return questPlayer;
    }

    public static Map<String, QuestProgress> createQuestProgressesForPlayer(ArrayList<Quest> quests) {
        if (quests == null) {
            return new HashMap<>();
        }
        return getQuestProgressesFromQuests(quests);
    }

    public static Map<String, QuestProgress> getQuestProgressesFromQuests(ArrayList<Quest> quests) {
        return quests != null
                ? quests.stream().collect(Collectors.toMap(quest -> quest.getId(), quest -> getQuestProgressFromQuest(quest)))
                : new HashMap<>();
    }

    public static QuestProgress getQuestProgressFromQuest(Quest quest) {
        return new QuestProgress(quest.getId(), 0, LocalDateTime.now());
    }

    public static List<Quest> getQuestsFromQuestPlayer(QuestPlayer questPlayer, List<Quest> quests) {
        return quests.stream()
                .filter(quest -> questPlayer.getQuestProgressMap().values().stream()
                        .anyMatch(value -> value.getQuestId().equals(quest.getId()))
                ).collect(Collectors.toList());
    }

    public static void checkAndAddMissingQuests(QuestPlayer questPlayer, List<Quest> quests) {
        if (quests == null || questPlayer == null) {
            return;
        }
        quests.forEach(quest -> {
            if (!questPlayer.getQuestProgressMap().containsKey(quest.getId())) {
                questPlayer.getQuestProgressMap().put(quest.getId(), getQuestProgressFromQuest(quest));
            }
        });
    }
}
