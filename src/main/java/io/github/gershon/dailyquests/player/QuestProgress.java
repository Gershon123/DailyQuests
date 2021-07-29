package io.github.gershon.dailyquests.player;

import java.time.LocalDateTime;

public class QuestProgress {
    String questId;
    int taskAmount;
    private LocalDateTime acceptedTime;
    private LocalDateTime completedTime;
    private boolean completed;

    public QuestProgress(String questId, int taskAmount, LocalDateTime acceptedTime) {
        this.questId = questId;
        this.taskAmount = taskAmount;
        this.acceptedTime = acceptedTime;
    }

    public String getQuestId() {
        return questId;
    }

    public int getTaskAmount() {
        return taskAmount;
    }

    public LocalDateTime getAcceptedTime() {
        return acceptedTime;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    public void setTaskAmount(int taskAmount) {
        this.taskAmount = taskAmount;
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setAcceptedTime(LocalDateTime acceptedTime) {
        this.acceptedTime = acceptedTime;
    }
}
