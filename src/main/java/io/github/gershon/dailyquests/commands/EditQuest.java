package io.github.gershon.dailyquests.commands;

import com.google.common.base.Splitter;
import com.pixelmonmod.pixelmon.enums.items.EnumApricorns;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import io.github.gershon.dailyquests.utils.QuestUtils;
import io.github.gershon.dailyquests.utils.TaskUtils;
import io.github.gershon.dailyquests.validation.ApricornValidation;
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

import java.util.Map;

public class EditQuest {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Edit a quest"))
            .permission("DailyQuests.command.edit")
            .arguments(
                    GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))),
                    GenericArguments.remainingJoinedStrings(Text.of("args"))
            )
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        String id = args.<String>getOne("id").get();
                        String cmdArgs = args.<String>getOne("args").get();
                        Quest quest = DailyQuests.getInstance().quests.get(id);
                        if (quest == null) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cUnable to find quest by id " + id)));
                            return CommandResult.success();
                        }
                        if (cmdArgs.length() == 0) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cNo changes specified")));
                            return CommandResult.success();
                        }

                        Map<String, String> properties = Splitter.on(" ").withKeyValueSeparator("=").split(cmdArgs);
                        handleEditing(player, quest, properties);
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aQuest " + quest.getId() + " has been updated!")));
                        DailyQuests.getInstance().updateQuests(DailyQuests.getInstance().getQuests());

                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }

    // To-do: Move this so it can interface with a menu?
    private void handleEditing(Player player, Quest quest, Map<String, String> properties) {
        Task task = quest.getTask();
        properties.keySet().forEach((key) -> {
            switch (key) {
                case "amount":
                    try {
                        task.setTotalAmount(Integer.parseInt(properties.get("amount")));
                    } catch (NumberFormatException ex) {
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cAmount " + properties.get("amount") + " is not a valid number.")));
                    }
                    break;
                case "title":
                    quest.setTitle(properties.get("title"));
                    break;
                case "category":
                    quest.setCategoryId(properties.get("category"));
                    break;
            }
        });


        switch (quest.getTask().getTaskType()) {
            case HARVEST_APRICORN:
                HarvestApricornTask apricornTask = (HarvestApricornTask) task;
                if (ApricornValidation.isValidApricorn(properties.get("apricorn"))) {
                    apricornTask.setApricorn(EnumApricorns.valueOf(properties.get("apricorn")));
                }
                if (properties.get("harvestany") != null) {
                    apricornTask.setHarvestAny(Boolean.parseBoolean(properties.get("apricorn")));
                }
        }
    }
}
