package io.github.gershon.dailyquests.quests.rewards;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public class CommandReward extends Reward {

    private String name;
    private String command;
    private transient RewardType rewardType = RewardType.COMMAND;

    public CommandReward(String name, String command) {
        this.name = name;
        this.command = command;
    }

    @Override
    public RewardType getRewardType() {
        return RewardType.COMMAND;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void giveReward(Player player) {
        Sponge.getCommandManager().process(
                Sponge.getServer().getConsole(), this.command.replaceAll("%player%", player.getName())
        );
    }
}
