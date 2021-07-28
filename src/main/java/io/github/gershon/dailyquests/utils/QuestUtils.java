package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskFactory;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QuestUtils {

    public static Quest createRepeatableQuest(String id, String title, TaskType taskType) {
        LocalDateTime time = LocalDateTime.now().plus(1, ChronoUnit.HOURS);
        ItemType itemType = ItemTypes.PAPER;
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        Task task = TaskFactory.createTask(title, taskType, 1);
        return new RepeatableQuest(id, title, task, rewards, itemType, QuestType.REPEATABLE, time);
    }

    public static boolean questExists(String id, ArrayList<Quest> quests) {
        return quests != null ? quests.stream().anyMatch(quest -> quest.getId().equals(id)) : false;
    }

    public static QuestPlayer createQuestPlayer(User user) {
        QuestPlayer questPlayer = new QuestPlayer(user.getUniqueId());
        questPlayer.setQuestProgressMap(createQuestProgressesForPlayer(DailyQuests.getInstance().getQuests()));
        return questPlayer;
    }

    public static Map<String, QuestProgress> createQuestProgressesForPlayer(ArrayList<Quest> quests) {
        if (quests == null) {
            return new HashMap<>();
        }
        if (quests.size() <= 3) {
            return getQuestProgressesFromQuests(quests);
        }
        ArrayList<Quest> randomQuests = new ArrayList<>();
        while (randomQuests.size() < 3) {
            Quest randomQuest = getRandomQuest(quests);
            if (!randomQuests.stream().anyMatch(quest -> quest.getId().equals(randomQuest.getId()))) {
                randomQuests.add(randomQuest);
            }
        }
        return getQuestProgressesFromQuests(randomQuests);
    }

    public static Map<String, QuestProgress> getQuestProgressesFromQuests(ArrayList<Quest> quests) {
        return quests != null
                ? quests.stream().collect(Collectors.toMap(quest -> quest.getId(), quest -> getQuestProgressFromQuest(quest)))
                : new HashMap<>();
    }

    public static QuestProgress getQuestProgressFromQuest(Quest quest) {
        return new QuestProgress(quest.getId(), 0);
    }

    public static Quest getQuest(List<Quest> quests, String id) {
        return quests != null
                ? quests.stream().filter(quest1 -> quest1.getId().equals(id)).findAny().orElse(null)
                : null;
    }

    public static List<Quest> getQuestsFromQuestPlayer(QuestPlayer questPlayer, List<Quest> quests) {
        return quests.stream()
                .filter(quest -> questPlayer.getQuestProgressMap().values().stream()
                        .anyMatch(value -> value.getQuestId().equals(quest.getId()))
                ).collect(Collectors.toList());
    }

    public static int getNextValidPosition(ArrayList<Quest> quests) {
        int position = 0;

        if (quests == null) {
            return 0;
        }

        for (int x = 0; x < quests.size() - 1; x++) {
            position++;
            if (position != quests.get(x + 1).getPosition()) {
                return position;
            }
        }

        return position;
    }

    public static void handleQuestTaskUpdate(Quest quest, QuestProgress questProgress, int amount, Player player) {
        questProgress.setTaskAmount(questProgress.getTaskAmount() + amount);
        if (questProgress.getTaskAmount() >= quest.getTask().getTotalAmount()) {
            quest.getTask().completeTask(player);
            quest.completeQuest(player);
        }
    }

    private static Quest getRandomQuest(ArrayList<Quest> quests) {
        int max = quests.size();
        return quests.get(ThreadLocalRandom.current().nextInt(0, max - 1));
    }
}
