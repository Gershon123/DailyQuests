package io.github.gershon.dailyquests.listeners;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerJoinListener {

    @Listener
    public void PlayerLogin(ClientConnectionEvent.Login e) {
        User p = e.getTargetUser();
        if (DailyQuests.getInstance().playerMap.get(p.getUniqueId()) == null) {
            DailyQuests.getInstance().playerMap.put(p.getUniqueId(), QuestUtils.createQuestListForPlayer(DailyQuests.getInstance().getQuests()));
        }
    }

}
