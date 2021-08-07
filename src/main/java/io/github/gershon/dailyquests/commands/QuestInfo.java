package io.github.gershon.dailyquests.commands;

import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Permissions;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.quests.tasks.impl.BaseItemTask;
import io.github.gershon.dailyquests.quests.tasks.impl.BasePokemonTask;
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

public class QuestInfo {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Shows quest info"))
            .permission(Permissions.INFO_QUESTS)
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
                        Category category = DailyQuests.getInstance().categories.get(quest.getCategoryId());
                        String categoryTitle = category != null ? category.getTitle() : "No category";
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bID: " + quest.getId())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTitle" + quest.getTitle())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bType: " + quest.getQuestType())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bCategory: " + categoryTitle)));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Type: " + quest.getTask().getTaskType())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Title: " + quest.getTask().getTitle())));
                        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bTask Amount: " + quest.getTask().getTotalAmount())));

                        TaskType taskType = quest.getTask().getTaskType();

                        if (taskType == null) {
                            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cTask is missing a type")));
                            return CommandResult.success();
                        }
                        switch (taskType) {
                            case HARVEST_APRICORN:
                                HarvestApricornTask apricornTask = (HarvestApricornTask) quest.getTask();
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bApricorn: " + apricornTask.getApricorn())));
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bAny?: " + apricornTask.isAny())));
                                break;
                            case HATCH_POKEMON:
                            case FISHING_POKEMON:
                            case DEFEAT_POKEMON:
                            case CATCH_POKEMON:
                                BasePokemonTask pokemonTask = (BasePokemonTask) quest.getTask();
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bSpecies: " + pokemonTask.getSpecies())));
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bShiny?: " + pokemonTask.isShiny())));
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bAny?: " + pokemonTask.isAny())));
                                break;
                            case SMELT_ITEM:
                            case CRAFT_ITEM:
                                BaseItemTask itemTask = (BaseItemTask) quest.getTask();
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bItem: " + itemTask.getItemType())));
                                player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&bAny?: " + itemTask.isAny())));
                                break;
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
