package io.github.gershon.dailyquests.quests.rewards;

public abstract class Reward {
    public abstract String getName();
    public abstract RewardType getRewardType();
    public abstract void giveReward();
}
