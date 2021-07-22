package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;
import java.util.List;

public class CaptureListener {

    @SubscribeEvent
    public void onSuccessfulCapture(CaptureEvent.SuccessfulCapture event) {
        Player player = (Player) event.player;
        List<Quest> quests = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        if (quests != null && quests.size() > 0) {
            List<Quest> captureQuests = QuestUtils.getQuestsForTask(quests, TaskType.CATCH_POKEMON);
            EntityPixelmon poke = event.getPokemon();
            QuestUtils.handleQuestsTaskUpdate(captureQuests, 1, player);
        }
    }

}
