package io.github.gershon.dailyquests.utils;

import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;

public enum Sounds {
    QUEST_COMPLETE(SoundTypes.ENTITY_PLAYER_LEVELUP, 1, 1.4),
    MENU_CLICK(SoundTypes.UI_BUTTON_CLICK, 1, 0.9),
    MENU_OPEN(SoundTypes.ENTITY_CHICKEN_EGG, 1, 1);

    private final SoundType sound;
    private final int volume;
    private final double pitch;

    Sounds(SoundType sound, int volume, double pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static void playSound(Player player, Sounds sound) {
        player.playSound(sound.sound, player.getLocation().getPosition(), sound.volume, sound.pitch);
    }
}
