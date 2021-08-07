package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.BattlePokemonTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class BeatWildPixelmonListener extends BaseListener {

    @SubscribeEvent
    public void defeatWild(BeatWildPixelmonEvent event) {
        List<Quest> quests = getQuestsForPlayer((Player) event.player);
        if (quests != null && quests.size() > 0) {
            for (PixelmonWrapper pw : event.wpp.allPokemon) {
                EntityPixelmon pokemon = pw.entity;
                pokemon.getPokemonData().getBaseStats().getTypeList();
                List<Quest> battleQuests = BattlePokemonTask.getApplicableQuests(quests, pokemon);
                updateQuestsForPlayer((Player) event.player, battleQuests, 1);
            }
        }
    }

}
