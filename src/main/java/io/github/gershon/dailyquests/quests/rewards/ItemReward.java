package io.github.gershon.dailyquests.quests.rewards;

import net.minecraft.item.Item;

public class ItemReward extends Reward {

    private String name;
    private int amount;
    private Item item;

    public ItemReward(String name, int amount, Item item) {
        this.name = name;
        this.amount = amount;
        this.item = item;
    }

    @Override
    public RewardType getRewardType() {
        return RewardType.ITEM;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void giveReward() {

    }
}
