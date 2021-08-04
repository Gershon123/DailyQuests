package io.github.gershon.dailyquests.quests.tasks;

import org.spongepowered.api.entity.living.player.Player;

public abstract class Task {
    private String title;
    private int totalAmount;
    private transient TaskType taskType;

    public Task(String title, TaskType taskType, int totalAmount) {
        this.title = title;
        this.taskType = taskType;
        this.totalAmount = totalAmount;
    }

    public Task(TaskType taskType) {
        this.taskType = taskType;
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

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void completeTask(Player player) {
    }

    public static boolean applicableTask(Task task, TaskType taskType) {
        if (task == null || task.getTaskType() == taskType) {
            return true;
        }
        return false;
    }

}
