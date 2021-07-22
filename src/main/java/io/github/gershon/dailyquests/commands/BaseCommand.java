package io.github.gershon.dailyquests.commands;

import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class BaseCommand {

    CommandSpec commandSpec = CommandSpec.builder()
            .description(Text.of("DailyQuests's base command"))
            .child(new CreateQuest().getCommandSpec(), "create")
            .child(new ListQuests().getCommandSpec(), "list")
            .child(new QuestInfo().getCommandSpec(), "info")
            .child(new MyQuests().getCommandSpec(), "myquests")
            .build();

    public CommandSpec getCommandSpec() {
        return commandSpec;
    }
}
