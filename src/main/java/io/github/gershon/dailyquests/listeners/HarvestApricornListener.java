package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class HarvestApricornListener extends BaseListener {

    @SubscribeEvent
    public void harvestApricorn(ApricornEvent.PickApricorn event) {
        List<Quest> quests = getQuestsForPlayer((Player) event.player);
        if (quests != null && quests.size() > 0) {
            List<Quest> apricornQuests = HarvestApricornTask.getApplicableQuests(quests, event.apricorn);
            updateQuestsForPlayer((Player) event.player, apricornQuests, event.getPickedStack().getCount());
        }
    }
}
