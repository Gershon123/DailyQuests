package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class BeatWildPixelmonListener {

    @SubscribeEvent
    public void defeatWild(BeatWildPixelmonEvent event) {
        Player player = (Player) event.player;
        List<Quest> quests = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        if (quests != null && quests.size() > 0) {
            List<Quest> apricornQuests = QuestUtils.getQuestsForTask(quests, TaskType.DEFEAT_POKEMON);
            QuestUtils.handleQuestsTaskUpdate(apricornQuests, 1, player);
        }
    }

}
