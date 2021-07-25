package io.github.gershon.dailyquests.player;

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
}
