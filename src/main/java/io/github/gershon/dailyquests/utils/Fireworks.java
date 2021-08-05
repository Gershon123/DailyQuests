package io.github.gershon.dailyquests.utils;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Firework;
import org.spongepowered.api.item.FireworkEffect;
import org.spongepowered.api.item.FireworkShapes;
import org.spongepowered.api.util.Color;

import java.util.ArrayList;

public class Fireworks {

    public static void questComplete(Player player) {
        ArrayList<FireworkEffect> effects = new ArrayList<>();
        effects.add(FireworkEffect.builder().shape(FireworkShapes.BALL).color(Color.YELLOW).flicker(true).trail(true).build());
        Firework firework = (Firework) player.getLocation().add(0, 2, 0).createEntity(EntityTypes.FIREWORK);
        firework.offer(Keys.FIREWORK_EFFECTS, effects);
        player.getLocation().spawnEntity(firework);
    }
}
