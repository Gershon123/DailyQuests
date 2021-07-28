package io.github.gershon.dailyquests.ui;

import com.mcsimonflash.sponge.teslalibs.inventory.Element;
import com.mcsimonflash.sponge.teslalibs.inventory.Layout;
import com.mcsimonflash.sponge.teslalibs.inventory.View;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.categories.Category;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;

public class QuestsMenu {

    public static void openMenu(Player player) {
        PluginContainer container = DailyQuests.getInstance().getPluginContainer();
        Layout layout = Layout.builder().dimension(InventoryDimension.of(9, 3)).build();

        View view = View.builder()
                .archetype(InventoryArchetypes.DOUBLE_CHEST)
                .property(InventoryTitle.of(Text.of("Quests")))
                .onClose(action -> action.getPlayer().sendMessage(Text.of("Goodbye!")))
                .build(container);
        view.open(player);
        view.define(layout);

        ArrayList<Category> categories = DailyQuests.getInstance().getCategories();
        if (categories != null) {
            for (Category category : categories) {
                DailyQuests.getInstance().getLogger().info(category.getTitle());
                DailyQuests.getInstance().getLogger().info(category.getPosition() + "");
                DailyQuests.getInstance().getLogger().info(category.itemType().toString());
                view.setElement(category.getPosition(), Element.of(ItemStack.of(category.itemType(), 1)));
            }
        }

    }
}
