package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.rewards.CommandReward;
import io.github.gershon.dailyquests.quests.rewards.ItemReward;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.rewards.RewardType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import io.github.gershon.dailyquests.utils.RewardUtils;
import io.github.gershon.dailyquests.utils.TextUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class AddReward {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Adds rewards to a quest"))
            .permission("DailyQuests.command.addreward")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("rewardtype"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("value")))
            )
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        String id = args.<String>getOne("id").get();
                        Quest quest = QuestUtils.getQuest(DailyQuests.getInstance().getQuests(), id);
                        if (quest == null) {
                            player.sendMessage(TextUtils.getText("&cUnable to find quest by id " + id));
                            return CommandResult.success();
                        }
                        String rewardtype = args.<String>getOne("rewardtype").get();

                        if (!RewardUtils.isValidReward(rewardtype)) {
                            player.sendMessage(TextUtils.getText("&cNot a valid reward type"));
                            return CommandResult.success();
                        }

                        String value = args.<String>getOne("value").get();
                        String name = args.<String>getOne("name").get();
                        Reward reward = null;

                        switch (RewardType.valueOf(rewardtype)) {
                            case COMMAND:
                                reward = new CommandReward(name, value);
                                break;
                            case ITEM:
                                reward = new ItemReward(name, 1, value);
                                break;
                        }
                        quest.getRewards().add(reward);
                        DailyQuests.getInstance().questStorage.storeQuest(quest);
                        player.sendMessage(TextUtils.getText("&aYou have added reward " + reward.getName() + " to " + quest.getTitle()));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
