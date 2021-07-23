package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import io.github.gershon.dailyquests.utils.QuestUtils;
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

import java.util.List;

public class QuestInfo {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Shows quest info"))
            .permission("DailyQuests.command.info")
            .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))))
            .executor(new CommandExecutor() {
                @Override
                public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                    if (src instanceof Player) {
                        Player player = (Player) src;
                        String id = args.<String>getOne("id").get();
                        Quest quest = QuestUtils.getQuest(DailyQuests.getInstance().getQuests(), id);
                        if (quest == null) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cUnable to find quest by id " + id)));
                            return CommandResult.success();
                        }
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&b" + quest.getTitle())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bID: " + quest.getId())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bType: " + quest.getQuestType())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Type: " + quest.getTask().getTaskType())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Title: " + quest.getTask().getTitle())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Amount: " + quest.getTask().getTotalAmount())));

                        switch (quest.getTask().getTaskType()) {
                            case HARVEST_APRICORN:
                                HarvestApricornTask task = (HarvestApricornTask) quest.getTask();
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bApricorn: " + task.getApricorn())));
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bHarvest Any?: " + task.isHarvestAny())));
                        }
                    }
                    return CommandResult.success();
                }
            })
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
