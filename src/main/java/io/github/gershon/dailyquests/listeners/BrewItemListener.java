package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.BrewItemTask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

public class BrewItemListener extends BaseListener {

    @SubscribeEvent
    public void brewItem(PlayerBrewedPotionEvent event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        if (entityPlayer == null) {
            return;
        }
        Player player = (Player) entityPlayer;
        List<Quest> quests = getQuestsForPlayer(player);
        if (quests != null && quests.size() > 0) {
            ItemStack itemStack = (ItemStack) (Object) event.getStack();
            List<Quest> smeltItemQuests = BrewItemTask.getApplicableQuests(quests, itemStack);
            updateQuestsForPlayer(player, smeltItemQuests, itemStack.getQuantity());
        }
    }
}
