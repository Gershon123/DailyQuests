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
import io.github.gershon.dailyquests.utils.CategoryUtils;
import io.github.gershon.dailyquests.utils.QuestUtils;
import io.github.gershon.dailyquests.utils.TextUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.time.Duration;
import java.util.ArrayList;
import java.util.function.Consumer;

public class QuestsMenu {

    public static void openMenu(Player player) {
        PluginContainer container = DailyQuests.getInstance().getPluginContainer();
        View view = View.builder()
                .archetype(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.of(TextUtils.getText("&e&lCategories")))
                .build(container)
                .define(Layout.builder()
                        .dimension(InventoryDimension.of(9, 3))
                        .build());

        view.open(player);

        ArrayList<Category> categories = DailyQuests.getInstance().getCategories();
        if (categories != null) {
            for (Category category : categories) {
                ArrayList<Text> loreList = new ArrayList<>();
                ArrayList<Quest> quests = CategoryUtils.getQuestsInCategory(category, DailyQuests.getInstance().getQuests());
                loreList.add(Text.of(""));
                loreList.add(TextUtils.getText("&7" + quests.size() + " Quests in total"));
                ItemStack itemStack = ItemStack.of(category.itemType(), 1);
                itemStack.offer(Keys.DISPLAY_NAME, Text.of(category.getTitle()));
                itemStack.offer(Keys.ITEM_LORE, loreList);

                Consumer<Action.Click> clickCategory = click -> Task.builder().execute(task -> {
                    player.closeInventory();
                    openCategory(player, category);
                    //player.playSound(SoundType.of());
                }).submit(DailyQuests.getInstance());

                view.setElement(category.getPosition(), Element.of(itemStack, clickCategory));
            }
        }
    }

    public static void openCategory(Player player, Category category) {
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        PluginContainer container = DailyQuests.getInstance().getPluginContainer();
        Layout layout = Layout.builder().dimension(InventoryDimension.of(9, 3)).build();

        View view = View.builder()
                .archetype(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.of(TextUtils.getText(category.getTitle())))
                .build(container);
        view.open(player);
        view.define(layout);

        ItemStack backButton = ItemStack.of(ItemTypes.MAP, 1);
        backButton.offer(Keys.DISPLAY_NAME, TextUtils.getText("&c< Back"));
        Consumer<Action.Click> back = click -> Task.builder().execute(task -> {
            player.closeInventory();
            openMenu(player);
        }).submit(DailyQuests.getInstance());
        view.setElement(0, Element.of(backButton, back));

        ArrayList<Quest> quests = CategoryUtils.getQuestsInCategory(category, DailyQuests.getInstance().getQuests());
        QuestUtils.checkAndAddMissingQuests(questPlayer, quests);
        if (quests != null) {
            for (Quest quest : quests) {
                QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
                ArrayList<Text> loreList = new ArrayList<>();
                ItemStack itemStack = ItemStack.of(quest.getIcon(), 1);

                quest.getLore().forEach(lore -> {
                    loreList.add(lore);
                });

                loreList.add(Text.of(""));
                loreList.add(TextUtils.progressBar(
                        questProgress.getTaskAmount(),
                        quest.getTask().getTotalAmount(),
                        TextUtils.getQuestProgress(quest, questPlayer)));
                if (questPlayer.getQuestProgressMap().get(quest.getId()).isCompleted()) {
                    loreList.add(Text.of(""));
                    loreList.add(TextUtils.getText("&a&lQUEST COMPLETE"));
                } else {
                    loreList.add(Text.of(""));
                    loreList.add(TextUtils.getText("&bRewards:"));
                    quest.getRewards().forEach(reward -> {
                        loreList.add(TextUtils.getText("&b- " + reward.getName()));
                    });
                }

                if (quest.getQuestType() == QuestType.REPEATABLE) {
                    if (questProgress.isCompleted()) {
                        RepeatableQuest repeatableQuest = (RepeatableQuest) quest;
                        Long cooldown = repeatableQuest.getCooldown(questProgress);
                        itemStack = ItemStack.of(ItemTypes.BARRIER, 1);
                        loreList.add(TextUtils.getText(
                                "&cCooldown: "
                                        + DurationFormatUtils.formatDuration(Math.max(cooldown, 0), "H:mm:ss", true)));
                    }
                }

                setItemStack(itemStack, quest, loreList);
                view.setElement(quest.getPosition() + 9, Element.of(itemStack));
            }
        }
    }

    private static void setItemStack(ItemStack itemStack, Quest quest, ArrayList<Text> loreList) {
        itemStack.offer(Keys.DISPLAY_NAME, TextUtils.getText(quest.getTitle()));
        itemStack.offer(Keys.ITEM_LORE, loreList);
    }
}
