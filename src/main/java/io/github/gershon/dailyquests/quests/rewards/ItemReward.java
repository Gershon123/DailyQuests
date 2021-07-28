package io.github.gershon.dailyquests.quests.rewards;

import io.github.gershon.dailyquests.utils.ItemUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemReward extends Reward {

    private String name;
    private int amount;
    private String item;
    private transient RewardType rewardType = RewardType.ITEM;

    public ItemReward(String name, int amount, String item) {
        this.name = name;
        this.amount = amount;
        this.item = item;
    }

    @Override
    public RewardType getRewardType() {
        return rewardType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public void giveReward(Player player) {
        ItemStack itemStack = ItemStack.of(ItemUtils.getItemType(item), amount);
        player.getInventory().offer(itemStack);
    }
}
