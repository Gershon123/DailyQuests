package io.github.gershon.dailyquests.player;

public class QuestProgress {
    String questId;
    int taskAmount;

    public QuestProgress(String questId, int taskAmount) {
        this.questId = questId;
        this.taskAmount = taskAmount;
    }

    public String getQuestId() {
        return questId;
    }

    public int getTaskAmount() {
        return taskAmount;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public void setTaskAmount(int taskAmount) {
        this.taskAmount = taskAmount;
    }
}
