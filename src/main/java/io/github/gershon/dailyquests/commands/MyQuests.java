package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.utils.QuestPlayerUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;

public class MyQuests {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Lists all quests"))
            .permission("DailyQuests.command.myquests")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        List<Quest> allQuests = DailyQuests.getInstance().getQuests();
                        List<Quest> quests = QuestPlayerUtils.getQuestsFromQuestPlayer(DailyQuests.getInstance().playerMap.get(player.getUniqueId()), allQuests);
                        if (quests == null || quests.isEmpty()) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cYou have no quests!")));
                            return CommandResult.success();
                        }
                        quests.forEach((quest -> {
                            player.sendMessage(
                                    Text.of(TextSerializers.FORMATTING_CODE.deserialize(
                                            "&b" + quest.getId() + " - " + quest.getTitle()
                                            )
                                    ));
                        }));
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
