package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

public class BeatWildPixelmonListener {

    @SubscribeEvent
    public void defeatWild(BeatWildPixelmonEvent event) {
        Player player = (Player) event.player;
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
    }

}
