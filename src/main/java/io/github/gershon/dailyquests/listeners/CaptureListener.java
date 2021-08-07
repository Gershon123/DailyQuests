package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.CapturePokemonTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class CaptureListener extends BaseListener {

    @SubscribeEvent
    public void onSuccessfulCapture(CaptureEvent.SuccessfulCapture event) {
        List<Quest> quests = getQuestsForPlayer((Player) event.player);
        if (quests != null && quests.size() > 0) {
            List<Quest> captureQuests = CapturePokemonTask.getApplicableQuests(quests, event.getPokemon());
            updateQuestsForPlayer((Player) event.player, captureQuests, 1);
        }
    }

}
