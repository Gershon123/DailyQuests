package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.CurryFinishedEvent;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.CurryTask;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Optional;

public class CurryListener extends BaseListener {

    @SubscribeEvent
    public void onCook(CurryFinishedEvent event) {
        Optional<Player> playerOptional = Sponge.getServer().getPlayer(event.player.getUniqueID());
        if (!playerOptional.isPresent()) {
            return;
        }
        List<Quest> quests = getQuestsForPlayer(playerOptional.get());
        if (quests != null && quests.size() > 0) {
            List<Quest> curryQuests = CurryTask.getApplicableQuests(quests, event.curryKey);
            updateQuestsForPlayer(playerOptional.get(), curryQuests, 1);
        }
    }

}
