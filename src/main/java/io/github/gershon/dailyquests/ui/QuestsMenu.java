package io.github.gershon.dailyquests.ui;

import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.QuestType;
import io.github.gershon.dailyquests.quests.RepeatableQuest;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.utils.*;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.function.Consumer;

public class QuestsMenu {

    public static void openMenu(Player player) {
        PluginContainer container = DailyQuests.getInstance().getPluginContainer();
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        if (questPlayer == null) {
            questPlayer = QuestPlayerUtils.createQuestPlayer(player);
            DailyQuests.getInstance().playerMap.put(player.getUniqueId(), questPlayer);
        }
        View view = View.builder()
                .archetype(InventoryArchetypes.CHEST)
                .property(InventoryTitle.of(TextUtils.getText("&e&lCategories")))
                .build(container)
                .define(Layout.builder().build());

        view.open(player);

        ArrayList<Category> categories = DailyQuests.getInstance().getCategories();
        if (categories != null) {
            for (Category category : categories) {
                ArrayList<Text> loreList = new ArrayList<>();
                category.getLore().forEach(lore -> {
                    loreList.add(lore);
                });
                ArrayList<Quest> quests = CategoryUtils.getQuestsInCategory(category, DailyQuests.getInstance().getQuests());
                QuestPlayerUtils.checkAndAddMissingQuests(questPlayer, quests);
                loreList.add(Text.of(""));
                loreList.add(TextUtils.getText("&7" + quests.size() + " Quests in total"));
                int completedQuests = 0;
                int availableQuests = 0;
                int cooldownQuests = 0;
                for (Quest quest : quests) {
                    if (quest.getQuestType() == QuestType.ONE_TIME && questPlayer.questIsComplete(quest.getId()))
                        completedQuests++;
                    else if (quest.getQuestType() == QuestType.REPEATABLE && questPlayer.questIsComplete(quest.getId()))
                        cooldownQuests++;
                    else if (!questPlayer.questIsComplete(quest.getId()))
                        availableQuests++;
                }
                loreList.add(TextUtils.getText("&a" + completedQuests + " Completed Quests"));
                loreList.add(TextUtils.getText("&b" + availableQuests + " Available Quests"));
                loreList.add(TextUtils.getText("&e" + cooldownQuests + " Quests are on cooldown"));
                ItemStack itemStack = ItemStack.of(category.itemType(), 1);
                itemStack.offer(Keys.DISPLAY_NAME, TextUtils.getText(category.getTitle()));
                itemStack.offer(Keys.ITEM_LORE, loreList);

                Consumer<Action.Click> clickCategory = click -> Task.builder().execute(task -> {
                    player.closeInventory();
                    openCategory(player, category);
                    Sounds.playSound(player, Sounds.MENU_CLICK);
                }).submit(DailyQuests.getInstance());

                view.setElement(category.getPosition(), Element.of(itemStack, clickCategory));
            }
        }
    }

    public static void openCategory(Player player, Category category) {
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        PluginContainer container = DailyQuests.getInstance().getPluginContainer();
        questPlayer.update();
        View view = getCategoryView(player, category, container);
        ArrayList<Quest> quests = CategoryUtils.getQuestsInCategory(category, DailyQuests.getInstance().getQuests());
        if (quests != null) {
            for (Quest quest : quests) {
                QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                ArrayList<Text> loreList = new ArrayList<>();
                ItemStack itemStack = ItemStack.of(quest.getIcon(), 1);
                itemStack = getItemStack(questPlayer, quest, questProgress, loreList, itemStack);

                view.setElement(quest.getPosition() + 9, Element.of(itemStack));
            }
        }
    }


    private static View getCategoryView(Player player, Category category, PluginContainer container) {
        Layout layout = Layout.builder().dimension(InventoryDimension.of(9, 2)).build();

        View view = View.builder()
                .archetype(InventoryArchetypes.CHEST)
                .property(InventoryTitle.of(TextUtils.getText(category.getTitle())))
                .build(container);
        view.open(player);
        view.define(layout);

        ItemStack backButton = ItemStack.of(ItemTypes.MAP, 1);
        backButton.offer(Keys.DISPLAY_NAME, TextUtils.getText("&c< Back"));
        Consumer<Action.Click> back = click -> Task.builder().execute(task -> {
            player.closeInventory();
            openMenu(player);
            Sounds.playSound(player, Sounds.MENU_CLICK);
        }).submit(DailyQuests.getInstance());
        view.setElement(0, Element.of(backButton, back));
        return view;
    }

    private static ItemStack getItemStack(QuestPlayer questPlayer, Quest quest, QuestProgress questProgress, ArrayList<Text> loreList, ItemStack itemStack) {
        quest.getLore().forEach(lore -> {
            loreList.add(lore);
        });

        loreList.add(Text.of(""));
        String taskProgress = quest.isInfoHidden(questPlayer)
                ? "&7(???/???)"
                : TextUtils.getQuestProgress(quest, questPlayer);

        loreList.add(TextUtils.progressBar(
                questProgress.getTaskAmount(),
                quest.getTask().getTotalAmount(),
                taskProgress));
        loreList.add(Text.of(""));
        loreList.add(TextUtils.getText("&bRewards:"));
        quest.getRewards().forEach(reward -> {
            loreList.add(TextUtils.getText("&b- " + reward.getName()));
        });
        if (questPlayer.getQuestProgressMap().get(quest.getId()).isCompleted()) {
            loreList.add(Text.of(""));
            loreList.add(TextUtils.getText("&a&lQUEST COMPLETE"));
        } else if (!quest.canCompleteQuest(questPlayer)) {
            itemStack = ItemStack.of(ItemTypes.BARRIER, 1);
            loreList.add(Text.of(""));
            loreList.add(TextUtils.getText("&c&lQUEST LOCKED"));
        }

        if (quest.getQuestType() == QuestType.REPEATABLE) {
            if (questProgress.isCompleted()) {
                RepeatableQuest repeatableQuest = (RepeatableQuest) quest;
                Long cooldown = repeatableQuest.getCooldown(questProgress);
                loreList.add(TextUtils.getText(
                        "&cCooldown: "
                                + DurationFormatUtils.formatDuration(Math.max(cooldown, 0), "H:mm:ss", true)));
            }
        }
        Text questTitle = TextUtils.getText(quest.isInfoHidden(questPlayer) ? "&c???" : quest.getTitle());
        setItemStack(itemStack, questTitle, loreList);
        return itemStack;
    }

    private static void setItemStack(ItemStack itemStack, Text title, ArrayList<Text> loreList) {
        itemStack.offer(Keys.DISPLAY_NAME, title);
        itemStack.offer(Keys.ITEM_LORE, loreList);
    }
}
