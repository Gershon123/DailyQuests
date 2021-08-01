package io.github.gershon.dailyquests.quests;

import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import org.spongepowered.api.item.ItemType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class RepeatableQuest extends Quest {

    private Duration frequency;

    public RepeatableQuest(
            String id,
            String title,
            Task task,
            ArrayList<Reward> rewards,
            ItemType icon,
            Duration frequency
    ) {
        super(id, title, task, rewards, icon, QuestType.REPEATABLE);
        this.frequency = frequency;
    }

    public RepeatableQuest() {
        super(QuestType.REPEATABLE);
    }

    public RepeatableQuest(String id) {
        super(id, QuestType.REPEATABLE);
    }

    public Duration getFrequency() {
        return frequency;
    }

    public void setTimeUnit(Duration frequency) {
        this.frequency = frequency;
    }

    public long getCooldown(QuestProgress questProgress) {
        long completedTime = questProgress.getCompletedTime().toInstant(ZoneOffset.UTC).toEpochMilli();
        long currentTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        return (completedTime + frequency.toMillis()) - currentTime;
    }
}
