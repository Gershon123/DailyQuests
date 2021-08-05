package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.ui.QuestsMenu;
import io.github.gershon.dailyquests.utils.Sounds;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class BaseCommand {

    CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("DailyQuests's base command"))
            .permission("DailyQuests.command.open")
            .child(new CreateQuest().getCommandSpec(), "create")
            .child(new ListQuests().getCommandSpec(), "list")
            .child(new QuestInfo().getCommandSpec(), "info")
            .child(new MyQuests().getCommandSpec(), "myquests")
            .child(new EditQuest().getCommandSpec(), "edit")
            .child(new CreateCategory().getCommandSpec(), "createcategory")
            .child(new ReloadQuests().getCommandSpec(), "reload")
            .child(new AddReward().getCommandSpec(), "addreward")
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        QuestsMenu.openMenu((Player) src);
                        Sounds.playSound((Player) src, Sounds.MENU_OPEN);
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
