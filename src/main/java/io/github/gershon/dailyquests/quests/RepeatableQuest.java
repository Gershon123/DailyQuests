package io.github.gershon.dailyquests.quests;

import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import net.minecraft.item.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepeatableQuest extends Quest {

    private LocalDateTime expiryTime;

    public RepeatableQuest(
            String id,
            String title,
            Task task,
            ArrayList<Reward> rewards,
            Item icon,
            QuestType questType,
            LocalDateTime expiryTime
    ) {
        super(id, title, task, rewards, icon, questType);
        this.expiryTime = expiryTime;
    }

    public RepeatableQuest(String id) {
        super(id);
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expiryTime);
    }
}
