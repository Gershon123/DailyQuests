package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Config;
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
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

public class CreateQuest {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Creates a quest"))
            .permission("DailyQuests.command.create")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("questname"))),
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("task")))
            )
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        String id = args.<String>getOne("id").get();
                        String name = args.<String>getOne("questname").get();
                        String task = args.<String>getOne("task").get();

                        if (QuestUtils.questExists(id, DailyQuests.getInstance().getQuests())) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cQuest with ID " + id + " already exists")));
                            return CommandResult.success();
                        }

                        if (!TaskUtils.isValidTask(task)) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cNot a valid quest task type")));
                            return CommandResult.success();
                        }

                        TaskType taskType = TaskType.valueOf(task);
                        DailyQuests.getInstance().addQuest(QuestUtils.createRepeatableQuest(id, name, taskType));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aCreated Quest: " + name)));

                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
