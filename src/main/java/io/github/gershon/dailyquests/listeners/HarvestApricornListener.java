package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class HarvestApricornListener {

    @SubscribeEvent
    public void harvestApricorn(ApricornEvent.PickApricorn event) {
        Player player = (Player) event.player;
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        List<Quest> quests = QuestUtils.getQuestsFromQuestPlayer(questPlayer, DailyQuests.getInstance().getQuests());
        if (quests != null && quests.size() > 0) {
            List<Quest> apricornQuests = HarvestApricornTask.getApplicableQuests(quests, event.apricorn);
            int amountPicked = event.getPickedStack().getCount();

            apricornQuests.forEach(quest -> {
                QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                QuestUtils.handleQuestTaskUpdate(quest, questProgress, amountPicked, player);
            });
        }
    }
}
