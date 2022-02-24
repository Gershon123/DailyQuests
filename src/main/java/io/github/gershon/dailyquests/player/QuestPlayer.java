package io.github.gershon.dailyquests.player;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestPlayer {
    UUID playerId;
    Map<String, QuestProgress> questProgressMap;
    int questsCompleted;

    public QuestPlayer(UUID playerId, Map<String, QuestProgress> questProgressMap, int questsCompleted) {
        this.playerId = playerId;
        this.questProgressMap = questProgressMap;
        this.questsCompleted = questsCompleted;
    }

    public QuestPlayer(UUID playerId) {
        this.playerId = playerId;
        this.questProgressMap = new HashMap<>();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public Map<String, QuestProgress> getQuestProgressMap() {
        return questProgressMap;
    }

    public int getQuestsCompleted() {
        return questsCompleted;
    }

    public void setQuestsCompleted(int questsCompleted) {
        this.questsCompleted = questsCompleted;
    }

    public void setQuestProgressMap(Map<String, QuestProgress> questProgressMap) {
        this.questProgressMap = questProgressMap;
    }

    public QuestProgress getQuestProgress(String id) {
        return questProgressMap.get(id);
    }

    public boolean questIsComplete(String id) {
        QuestProgress questProgress = getQuestProgress(id);
        return questProgress != null ? questProgress.isCompleted() : false;
    }

    public boolean hasPermission(String permission) {
        Player player = Sponge.getServer().getPlayer(getPlayerId()).orElse(null);
        return player != null && player.hasPermission(permission);
    }

    public void update() {
        ArrayList<String> questsToRemove = new ArrayList<>();
        for (String key : questProgressMap.keySet()) {
            QuestProgress questProgress = questProgressMap.get(key);
            Quest quest = DailyQuests.getInstance().quests.get(questProgress.getQuestId());
            if (quest == null) {
                questsToRemove.add(questProgress.getQuestId());
                continue;
            }
            if (quest.getQuestType() == QuestType.REPEATABLE && questProgress.isCompleted()) {
                RepeatableQuest repeatableQuest = (RepeatableQuest) quest;
                if (repeatableQuest.getCooldown(questProgress) <= 0) {
                    questProgressMap.put(key, QuestPlayerUtils.getQuestProgressFromQuest(quest));
                }
            }
        }
        for (String key : questsToRemove) {
            if (key != null) {
                questProgressMap.remove(key);
            }
        }
    }
}
