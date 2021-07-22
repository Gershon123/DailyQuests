package io.github.gershon.dailyquests.quests.rewards;

public class CommandReward extends Reward {

    private String name;
    private String command;

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
    public void giveReward() {

    }
}
