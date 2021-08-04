package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.CraftItemTask;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.CraftItemEvent;

import java.util.List;

public class CraftItemListener extends BaseListener {

    @Listener
    public void craftItem(CraftItemEvent.Craft event, @First Player player) {
        List<Quest> quests = getQuestsForPlayer(player);
        if (quests != null && quests.size() > 0) {
            List<Quest> craftItemQuests = CraftItemTask.getApplicableQuests(quests, event.getCrafted().getType());
            updateQuestsForPlayer(player, craftItemQuests, event.getCrafted().getQuantity());
        }
    }
}
