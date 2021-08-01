package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.CapturePokemonTask;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class CaptureListener {

    @SubscribeEvent
    public void onSuccessfulCapture(CaptureEvent.SuccessfulCapture event) {
        Player player = (Player) event.player;
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        List<Quest> quests = QuestPlayerUtils.getQuestsFromQuestPlayer(questPlayer, DailyQuests.getInstance().getQuests());
        if (quests != null && quests.size() > 0) {
            List<Quest> captureQuests = CapturePokemonTask.getApplicableQuests(quests, event.getPokemon().getSpecies());

            captureQuests.forEach(quest -> {
                QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                QuestUtils.handleQuestTaskUpdate(quest, questProgress, 1, player);
            });
        }
    }

}
