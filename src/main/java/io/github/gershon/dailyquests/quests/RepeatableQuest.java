package io.github.gershon.dailyquests.quests;

import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import org.spongepowered.api.item.ItemType;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeatableQuest extends Quest {

    private LocalDateTime expiryTime;

    public RepeatableQuest(
            String id,
            String title,
            Task task,
            ArrayList<Reward> rewards,
            ItemType icon,
            QuestType questType,
            LocalDateTime expiryTime
    ) {
        super(id, title, task, rewards, icon, questType);
        this.expiryTime = expiryTime;
    }

    public RepeatableQuest() {
        super(QuestType.REPEATABLE);
    }


    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expiryTime);
    }
}
