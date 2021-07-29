package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.HashMap;

public class ReloadQuests {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Reloads the plugin"))
            .permission("DailyQuests.command.reload")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
                    DailyQuests.getInstance().quests = new HashMap<>();
                    DailyQuests.getInstance().categories = new HashMap<>();
                    DailyQuests.getInstance().loadItems();
                    src.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aQuests Reloaded!")));
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
