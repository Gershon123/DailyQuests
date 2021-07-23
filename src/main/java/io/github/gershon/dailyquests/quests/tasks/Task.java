package io.github.gershon.dailyquests.quests.tasks;

import org.spongepowered.api.entity.living.player.Player;

public class Task {
    private String title;
    private TaskType taskType;
    private int totalAmount;
    private int currentAmount;

    public Task(String title, TaskType taskType, int totalAmount) {
        this.title = title;
        this.taskType = taskType;
        this.totalAmount = totalAmount;
        this.currentAmount = 0;
    }

    public String getTitle() {
        return title;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void completeTask(Player player) {}
}
