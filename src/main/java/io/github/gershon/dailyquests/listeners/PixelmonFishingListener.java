package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.FishingEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.FishingPokemonTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class PixelmonFishingListener extends BaseListener {

    @SubscribeEvent
    public void onCatch(FishingEvent.Reel event) {
        List<Quest> quests = getQuestsForPlayer((Player) event.player);
        if (event.optEntity.isPresent() && event.optEntity.get() instanceof EntityPixelmon) {
            if (quests != null && quests.size() > 0) {
                EntityPixelmon pokemon = (EntityPixelmon) event.optEntity.get();
                List<Quest> fishingQuests = FishingPokemonTask.getApplicableQuests(quests, pokemon.getSpecies());
                updateQuestsForPlayer((Player) event.player, fishingQuests, 1);
            }
        }
    }

}
