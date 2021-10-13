package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Permissions;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.utils.QuestUtils;
import org.spongepowered.api.Sponge;
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

import java.util.Optional;

public class CompleteQuest {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Completes a quest"))
            .permission(Permissions.COMPLETE_QUESTS_COMMAND)
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("player")))
            )
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    String id = args.<String>getOne("id").get();
                    String playerName = args.<String>getOne("player").get();

                    Quest quest = DailyQuests.getInstance().quests.get(id);
                    Optional<Player> playerOptional = Sponge.getServer().getPlayer(playerName);
                    if (quest == null) {
                        src.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cUnable to find quest by id " + id)));
                        return CommandResult.success();
                    }
                    if (!playerOptional.isPresent()) {
                        src.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cUnable to find player " + playerName)));
                        return CommandResult.success();
                    }

                    Player player = playerOptional.get();
                    QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
                    QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                    questProgress.setTaskAmount(0);
                    QuestUtils.handleQuestTaskUpdate(quest, questProgress, quest.getTask().getTotalAmount(), player);
                    src.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aSuccessfully completed quest " + id + " for " + playerName)));
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
