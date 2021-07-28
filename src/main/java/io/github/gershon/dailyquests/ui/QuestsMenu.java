package io.github.gershon.dailyquests.ui;

import com.mcsimonflash.sponge.teslalibs.inventory.Action;
import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.utils.CategoryUtils;
import io.github.gershon.dailyquests.utils.TextUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundType;
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
        if (quests != null) {
            for (Quest quest : quests) {
                ArrayList<Text> loreList = new ArrayList<>();
                loreList.add(Text.of(""));
                loreList.add(TextUtils.progressBar(
                        questPlayer.getQuestProgressMap().get(quest.getId()).getTaskAmount(),
                        quest.getTask().getTotalAmount(),
                        TextUtils.getQuestProgress(quest, questPlayer)));
                loreList.add(Text.of(""));
                loreList.add(TextUtils.getText("&bRewards:"));
                quest.getRewards().forEach(reward -> {
                    loreList.add(TextUtils.getText("&b- " + reward.getName()));
                });
                ItemStack itemStack = ItemStack.of(quest.getIcon(), 1);
                itemStack.offer(Keys.DISPLAY_NAME, TextUtils.getText(quest.getTitle()));
                itemStack.offer(Keys.ITEM_LORE, loreList);

                view.setElement(quest.getPosition() + 9, Element.of(itemStack));
            }
        }
    }
}
