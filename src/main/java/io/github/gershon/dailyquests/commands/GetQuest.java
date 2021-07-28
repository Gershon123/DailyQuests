package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import io.github.gershon.dailyquests.utils.TaskUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class GetQuest {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Gets a quest"))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;

                        DailyQuests.getInstance().playerMap.put(player.getUniqueId(), QuestUtils.createQuestPlayer(player));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
