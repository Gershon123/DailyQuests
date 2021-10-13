package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.OneTimeQuest;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskFactory;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.temporal.ChronoUnit.HOURS;

public class QuestUtils {

    public static Quest createQuest(String id, String title, QuestType questType, TaskType taskType) {
        ItemType itemType = ItemTypes.PAPER;
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        Task task = TaskFactory.createTask(title, taskType, 1);

        switch (questType) {
            case REPEATABLE:
                Duration duration = Duration.of(1, HOURS);
                return new RepeatableQuest(id, title, task, rewards, itemType, duration);
            default:
            case ONE_TIME:
                return new OneTimeQuest(id, title, task, rewards, itemType);
        }
    }

    public static boolean questExists(String id, ArrayList<Quest> quests) {
        return quests != null ? quests.stream().anyMatch(quest -> quest.getId().equals(id)) : false;
    }

    public static Quest getQuest(List<Quest> quests, String id) {
        return quests != null
                ? quests.stream().filter(quest1 -> quest1.getId().equals(id)).findAny().orElse(null)
                : null;
    }

    public static int getNextValidPosition(ArrayList<Quest> quests) {
        int position = 0;
        ArrayList<Integer> positions = new ArrayList<Integer>();
        quests.forEach(quest -> {
            positions.add(quest.getPosition());
        });

        while (positions.contains(position)) {
            position++;
        }
        return position;
    }

    public static void handleQuestTaskUpdate(Quest quest, QuestProgress questProgress, int amount, Player player) {
        if (player == null || quest == null || questProgress == null || questProgress.isCompleted()) {
            return;
        }

        questProgress.setTaskAmount(questProgress.getTaskAmount() + amount);
        if (questProgress.getTaskAmount() >= quest.getTask().getTotalAmount()) {
            quest.getTask().completeTask(player);
            quest.completeQuest(player);
        }
    }

    public static Quest getRandomQuest(ArrayList<Quest> quests) {
        int max = quests.size();
        return quests.get(ThreadLocalRandom.current().nextInt(0, max - 1));
    }
}
