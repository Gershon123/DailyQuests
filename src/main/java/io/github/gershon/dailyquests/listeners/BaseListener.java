package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class BaseListener {

    public List<Quest> getQuestsForPlayer(Player player) {
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        return QuestPlayerUtils.getQuestsFromQuestPlayer(questPlayer, DailyQuests.getInstance().getQuests());
    }

    public void updateQuestsForPlayer(Player player, List<Quest> quests, int amount) {
        if (player == null || quests == null) {
            return;
        }
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        quests.forEach(quest -> {
            QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
            QuestUtils.handleQuestTaskUpdate(quest, questProgress, amount, player);
        });
    }
}
