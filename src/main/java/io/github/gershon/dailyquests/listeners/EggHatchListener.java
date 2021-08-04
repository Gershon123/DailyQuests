package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.EggHatchEvent;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.EggHatchTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class EggHatchListener extends BaseListener {

    @SubscribeEvent
    public void onEggHatch(EggHatchEvent event) {
        Player player = (Player) event.pokemon.getOwnerPlayer();
        List<Quest> quests = getQuestsForPlayer(player);
        if (quests != null && quests.size() > 0) {
            List<Quest> captureQuests = EggHatchTask.getApplicableQuests(quests, event.pokemon.getSpecies());
            updateQuestsForPlayer(player, captureQuests, 1);
        }
    }
}
