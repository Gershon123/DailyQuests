package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskFactory;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QuestUtils {

    public static Quest createRepeatableQuest(String id, String title, TaskType taskType) {
        LocalDateTime time = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        Task task = TaskFactory.createTask(title, taskType, 1);
        return new RepeatableQuest(id, title, task, rewards, null, QuestType.REPEATABLE, time);
    }

    public static boolean questExists(String id, ArrayList<Quest> quests) {
        return quests != null ? quests.stream().anyMatch(quest -> quest.getId().equals(id)) : false;
    }

    public static ArrayList<Quest> createQuestListForPlayer(ArrayList<Quest> quests) {
        if (quests == null) {
            return new ArrayList<>();
        }
        if (quests.size() <= 3) {
            return quests;
        }
        ArrayList<Quest> randomQuests = new ArrayList<>();
        while (randomQuests.size() < 3) {
            Quest randomQuest = getRandomQuest(quests);
            if (!randomQuests.stream().anyMatch(quest -> quest.getId().equals(randomQuest.getId()))) {
                randomQuests.add(randomQuest);
            }
        }
        return randomQuests;
    }

    public static Quest getQuest(List<Quest> quests, String id) {
        return quests.stream().filter(quest1 -> quest1.getId().equals(id)).findAny().orElse(null);
    }

    public static List<Quest> removeQuest(List<Quest> quests, String id) {
        return quests.stream().filter(quest -> !quest.getId().equals(id)).collect(Collectors.toList());
    }

    public static List<Quest> getQuestsForTask(List<Quest> quests, TaskType taskType) {
        return quests.stream().filter(quest -> quest.getTask().getTaskType().equals(taskType)).collect(Collectors.toList());
    }

    public static void handleQuestsTaskUpdate(List<Quest> quests, int amount, Player player) {
        quests.forEach(quest -> {
            quest.getTask().setCurrentAmount(quest.getTask().getCurrentAmount() + amount);
            if (quest.getTask().getCurrentAmount() >= quest.getTask().getTotalAmount()) {
                quest.getTask().completeTask(player);
                quest.completeQuest(player);
            }
        });
    }

    private static Quest getRandomQuest(ArrayList<Quest> quests) {
        int max = quests.size();
        return quests.get(ThreadLocalRandom.current().nextInt(0, max - 1));
    }
}
