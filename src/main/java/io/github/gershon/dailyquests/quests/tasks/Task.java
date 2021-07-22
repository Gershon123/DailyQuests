package io.github.gershon.dailyquests.quests.tasks;

public class Task {
    private String title;
    private TaskType taskType;
    private int amount;

    public Task(String title, TaskType taskType, int amount) {
        this.title = title;
        this.taskType = taskType;
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
