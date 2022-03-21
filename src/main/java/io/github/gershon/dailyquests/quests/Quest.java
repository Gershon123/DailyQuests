package io.github.gershon.dailyquests.quests;

import com.google.gson.annotations.Expose;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.config.Permissions;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.*;
import net.minecraft.item.Item;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Firework;
import org.spongepowered.api.item.FireworkEffect;
import org.spongepowered.api.item.FireworkShapes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Quest {
    private String id;
    private String title;
    private String categoryId;
    private String requiredQuestId;
    private int position;
    private Task task;
    private ArrayList<Reward> rewards;
    private ArrayList<String> lore;
    private String icon;
    private boolean requirePermission;
    private boolean infoHidden;
    private transient QuestType questType;

    public Quest(String id, String title, Task task, ArrayList<Reward> rewards, ItemType icon, QuestType questType) {
        this.id = id;
        this.rewards = rewards;
        this.task = task;
        this.title = title;
        try {
            this.icon = icon.getId();
        } catch (Exception e) {
            this.icon = "minecraft:paper";
        }
        this.questType = questType;
        this.lore = new ArrayList<>();
        try {
            this.position = QuestUtils.getNextValidPosition(DailyQuests.getInstance().getQuests());
        } catch (Exception e) {
            this.position = 0;
        }
    }

    public Quest(String id, QuestType questType) {
        this.id = id;
        this.questType = questType;
        this.rewards = new ArrayList<>();
        this.lore = new ArrayList<>();
    }

    public Quest(QuestType questType) {
        this.questType = questType;
    }

    public Quest() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getRequiredQuestId() {
        return requiredQuestId;
    }

    public int getPosition() {
        return position;
    }

    public Task getTask() {
        return task;
    }

    public ArrayList<Reward> getRewards() {
        if (rewards == null) {
            return new ArrayList<>();
        }
        return rewards;
    }

    public ItemType getIcon() {
        return Sponge.getRegistry().getType(ItemType.class, icon).get();
    }

    public boolean isRequirePermission() {
        return requirePermission;
    }

    public boolean isInfoHidden(QuestPlayer player) {
        return infoHidden && !canCompleteQuest(player);
    }

    public String getPermission() {
        return DailyQuests.ID + ".quests." + id;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public List<Text> getLore() {
        if (lore == null) {
            return new ArrayList<>();
        }
        return lore.stream().map(lore -> TextUtils.getText(lore)).collect(Collectors.toList());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setRequiredQuestId(String requiredQuestId) {
        this.requiredQuestId = requiredQuestId;
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public void setInfoHidden(boolean infoHidden) {
        this.infoHidden = infoHidden;
    }

    public boolean canCompleteQuest(QuestPlayer player) {
        if (requiredQuestId != null) {
            Quest requiredQuest = DailyQuests.getInstance().quests.get(requiredQuestId);
            if (requiredQuest != null && !player.getQuestProgressMap().get(requiredQuestId).isCompleted()) {
                return false;
            }
        }
        if (!player.hasPermission(Permissions.COMPLETE_QUESTS) ||
                (requirePermission && !player.hasPermission(getPermission()))) {
            return false;
        }
        return true;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void completeQuest(Player player) {
        try {
            DailyQuests.getInstance().getLogger().info(player.getName() + " completed quest " + id);
            QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
            questPlayer.getQuestProgressMap().get(id).setCompleted(true);
            questPlayer.getQuestProgressMap().get(id).setCompletedTime(LocalDateTime.now());
            getRewards().forEach(reward -> reward.giveReward(player));
            player.sendMessage(TextUtils.getText("&aYou have completed the " + getTitle() + " &r&aquest!"));
            Sounds.playSound(player, Sounds.QUEST_COMPLETE);
            Fireworks.questComplete(player);
        } catch (Exception e) {
            DailyQuests.getInstance().getLogger().error("Error completing quest " + this.id + " for " + player.getName());
            DailyQuests.getInstance().getLogger().error(e.getMessage());
        }
    }
}
