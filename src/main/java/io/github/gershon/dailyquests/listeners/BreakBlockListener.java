package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.BreakBlockTask;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.item.ItemType;

import java.util.List;
import java.util.Optional;

public class BreakBlockListener extends BaseListener {

    @Listener(order = Order.LATE)
    public void PlayerBreaksBlock(ChangeBlockEvent.Break e, @Root Player player) {

        if (e == null || player == null || e.isCancelled() || e.getTransactions() == null || e.getTransactions().size() == 0) {
            return;
        }

        BlockSnapshot transaction = e.getTransactions().get(0).getOriginal();
        if (transaction == null || transaction.getCreator().isPresent()) {
            return;
        }

        Optional<ItemType> itemOptional = transaction.getState().getType().getItem();
        if (!itemOptional.isPresent()) {
            return;
        }

        List<Quest> quests = getQuestsForPlayer(player);
        if (quests != null && quests.size() > 0) {
            List<Quest> breakBlockQuests = BreakBlockTask.getApplicableQuests(quests, itemOptional.get());
            updateQuestsForPlayer(player, breakBlockQuests, 1);
        }
    }
}
