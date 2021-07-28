package io.github.gershon.dailyquests.quests.rewards;

import org.spongepowered.api.entity.living.player.Player;

public abstract class Reward {

    public abstract String getName();

    public abstract RewardType getRewardType();

    public abstract void giveReward(Player player);
}
