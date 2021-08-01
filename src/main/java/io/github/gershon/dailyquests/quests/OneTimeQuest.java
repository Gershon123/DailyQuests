package io.github.gershon.dailyquests.quests;

import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import org.spongepowered.api.item.ItemType;

import java.util.ArrayList;

public class OneTimeQuest extends Quest {

    public OneTimeQuest(
            String id,
            String title,
            Task task,
            ArrayList<Reward> rewards,
            ItemType icon
    ) {
        super(id, title, task, rewards, icon, QuestType.ONE_TIME);
    }

    public OneTimeQuest() {
        super(QuestType.ONE_TIME);
    }

    public OneTimeQuest(String id) {
        super(id, QuestType.ONE_TIME);
    }
}
