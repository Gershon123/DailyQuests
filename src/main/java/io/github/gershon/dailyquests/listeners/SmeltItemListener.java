package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.SmeltItemTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class SmeltItemListener extends BaseListener {

    @SubscribeEvent
    public void smeltItem(PlayerEvent.ItemSmeltedEvent event) {
        if (event.player == null) {
            return;
        }
        Optional<Player> playerOptional = Sponge.getServer().getPlayer(event.player.getUniqueID());
        if (!playerOptional.isPresent() || playerOptional == null) {
            return;
        }
        List<Quest> quests = getQuestsForPlayer(playerOptional.get());
        if (quests != null && quests.size() > 0) {
            List<Quest> smeltItemQuests = SmeltItemTask.getApplicableQuests(quests, (ItemStack) (Object) event.smelting);
            updateQuestsForPlayer(playerOptional.get(), smeltItemQuests, event.smelting.getCount());
        }
    }
}
