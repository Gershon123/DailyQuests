package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.CraftItemTask;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.CraftItemEvent;

import java.util.List;

public class CraftItemListener {

    @Listener
    public void craftItem(CraftItemEvent.Craft event, @First Player player) {
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        List<Quest> quests = QuestPlayerUtils.getQuestsFromQuestPlayer(questPlayer, DailyQuests.getInstance().getQuests());
        if (quests != null && quests.size() > 0) {
            List<Quest> apricornQuests = CraftItemTask.getApplicableQuests(quests, event.getCrafted().getType());
            int amountCrafted = event.getCrafted().getQuantity();

            apricornQuests.forEach(quest -> {
                QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                QuestUtils.handleQuestTaskUpdate(quest, questProgress, amountCrafted, player);
            });
        }
    }
}
