package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.BattlePokemonTask;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class BeatWildPixelmonListener {

    @SubscribeEvent
    public void defeatWild(BeatWildPixelmonEvent event) {
        Player player = (Player) event.player;
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        List<Quest> quests = QuestPlayerUtils.getQuestsFromQuestPlayer(questPlayer, DailyQuests.getInstance().getQuests());
        if (quests != null && quests.size() > 0) {
            DailyQuests.getInstance().getLogger().info(event.wpp.allPokemon.length + "");
            for (PixelmonWrapper pw : event.wpp.allPokemon) {
                EntityPixelmon pokemon = pw.entity;
                List<Quest> captureQuests = BattlePokemonTask.getApplicableQuests(quests, pokemon.getSpecies());
                captureQuests.forEach(quest -> {
                    QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                    QuestUtils.handleQuestTaskUpdate(quest, questProgress, 1, player);
                });
            }
        }
    }

}
