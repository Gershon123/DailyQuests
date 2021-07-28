package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.ui.QuestsMenu;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class OpenQuests {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Opens the quest menu"))
            .permission("DailyQuests.command.open")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        QuestsMenu.openMenu((Player) src);
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
