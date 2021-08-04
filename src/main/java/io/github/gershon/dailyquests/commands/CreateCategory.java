package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Permissions;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.utils.CategoryUtils;
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

public class CreateCategory {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Creates a category"))
            .permission(Permissions.CREATE_CATEGORIES)
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("title")))
            )
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        String id = args.<String>getOne("id").get();
                        String name = args.<String>getOne("title").get();

                        if (CategoryUtils.categoryExists(id, DailyQuests.getInstance().getCategories())) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cCategory with ID " + id + " already exists")));
                            return CommandResult.success();
                        }

                        DailyQuests.getInstance().categoryStorage.saveCategory(new Category(id, name));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aCreated Category: " + name)));

                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
