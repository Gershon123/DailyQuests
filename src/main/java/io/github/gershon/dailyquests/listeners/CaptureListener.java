package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

public class CaptureListener {

    @SubscribeEvent
    public void onSuccessfulCapture(CaptureEvent.SuccessfulCapture event) {
        Player player = (Player) event.player;
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
    }

}
