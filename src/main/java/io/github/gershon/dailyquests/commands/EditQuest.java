package io.github.gershon.dailyquests.commands;

import com.google.common.base.Splitter;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.enums.items.EnumApricorns;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Permissions;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.impl.BasePokemonTask;
import io.github.gershon.dailyquests.quests.tasks.impl.CapturePokemonTask;
import io.github.gershon.dailyquests.quests.tasks.impl.CraftItemTask;
import io.github.gershon.dailyquests.quests.tasks.impl.HarvestApricornTask;
import io.github.gershon.dailyquests.utils.QuestUtils;
import io.github.gershon.dailyquests.validation.ApricornValidation;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Map;
import java.util.Optional;

public class EditQuest {

    private CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("Edit a quest"))
            .permission(Permissions.EDIT_QUESTS)
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
                    task.setTotalAmount(parseNumber(properties.get("amount"), player));
                    break;
                case "title":
                    quest.setTitle(properties.get("title"));
                    break;
                case "category":
                    quest.setCategoryId(properties.get("category"));
                    break;
                case "icon":
                    quest.setIcon(properties.get("icon"));
                    break;
                case "position":
                    quest.setPosition(parseNumber(properties.get("position"), player));
                    break;
                case "requiredQuestId":
                    if (QuestUtils.questExists(properties.get("requiredQuestId"), DailyQuests.getInstance().getQuests())) {
                        quest.setRequiredQuestId(properties.get("requiredQuestId"));
                    }
            }
        });


        switch (quest.getTask().getTaskType()) {
            case HARVEST_APRICORN:
                final HarvestApricornTask apricornTask = (HarvestApricornTask) task;
                if (ApricornValidation.isValidApricorn(properties.get("apricorn"))) {
                    apricornTask.setApricorn(EnumApricorns.valueOf(properties.get("apricorn")));
                }
                if (properties.get("any") != null) {
                    apricornTask.setAny(Boolean.parseBoolean(properties.get("any")));
                }
                break;
            case DEFEAT_POKEMON:
            case HATCH_POKEMON:
            case CATCH_POKEMON:
                final BasePokemonTask capturePokemonTask = (BasePokemonTask) task;
                if (EnumSpecies.hasPokemon(properties.get("pokemon"))) {
                    capturePokemonTask.setSpecies(EnumSpecies.valueOf(properties.get("pokemon")));
                }
                if (properties.get("any") != null) {
                    capturePokemonTask.setAny(Boolean.parseBoolean(properties.get("any")));
                }
                if (properties.get("shiny") != null) {
                    capturePokemonTask.setShiny(Boolean.parseBoolean(properties.get("shiny")));
                }
                break;
            case SMELT_ITEM:
            case CRAFT_ITEM:
                CraftItemTask craftItemTask = (CraftItemTask) task;
                if (properties.get("itemType") != null) {
                    final Optional<ItemType> itemType = Sponge.getRegistry().getType(ItemType.class, properties.get("itemType"));
                    if (itemType != null && itemType.isPresent()) {
                        craftItemTask.setItemType(itemType.get().getId());
                    }
                }
                if (properties.get("any") != null) {
                    craftItemTask.setAny(Boolean.parseBoolean(properties.get("any")));
                }
                break;
        }
    }

    private int parseNumber(String input, Player player) {
        int number = 0;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&cAmount " + input + " is not a valid number.")));
        }
        return number;
    }
}
