package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.scheduler.Task;

public class PlayerJoinListener {

    @Listener
    public void PlayerLogin(ClientConnectionEvent.Login e) {
        User p = e.getTargetUser();
        if (DailyQuests.getInstance().playerMap.get(p.getUniqueId()) == null) {
            Task.builder().execute(() -> DailyQuests.getInstance().getQuestPlayerStorage().loadPlayer(p.getUniqueId()))
                    .async()
                    .submit(DailyQuests.getInstance());
        }
    }

    @Listener
    public void PlayerDisconnect(ClientConnectionEvent.Disconnect e) {
        Player spongePlayer = e.getTargetEntity();
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(spongePlayer.getUniqueId());
        if (questPlayer != null) {
            Task.builder().execute(() -> DailyQuests.getInstance().getQuestPlayerStorage().savePlayer(questPlayer))
                    .async()
                    .submit(DailyQuests.getInstance());
        } else {
            DailyQuests.getInstance().getLogger().warn("Failed to save " + spongePlayer.getName());
        }
    }

}
