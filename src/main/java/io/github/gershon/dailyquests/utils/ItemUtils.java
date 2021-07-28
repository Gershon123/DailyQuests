package io.github.gershon.dailyquests.utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;

public class ItemUtils {

    public static ItemType getItemType(String item) {
        return Sponge.getRegistry().getType(ItemType.class, item).get();
    }
}
