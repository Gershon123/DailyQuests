package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.BeatTrainerEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;

public class BeatTrainerListener {

    @SubscribeEvent
    public void defeatNPC(BeatTrainerEvent event) {

        Player player = (Player) event.player;
        NPCTrainer trainer = event.trainer;

    }

}
