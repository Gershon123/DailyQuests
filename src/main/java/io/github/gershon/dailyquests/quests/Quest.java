package io.github.gershon.dailyquests.quests;

import com.google.gson.annotations.Expose;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.quests.categories.Category;
import io.github.gershon.dailyquests.quests.rewards.Reward;
import io.github.gershon.dailyquests.quests.tasks.Task;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraft.item.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.awt.*;
import java.util.ArrayList;

public abstract class Quest {
    private String id;
    private String title;
    private String categoryId;
    private Point point;
    private Task task;
    private ArrayList<Reward> rewards;
    private ItemType icon;
    private transient QuestType questType;

    public Quest(String id, String title, Task task, ArrayList<Reward> rewards, ItemType icon, QuestType questType) {
        this.id = id;
        this.rewards = rewards;
        this.task = task;
        this.title = title;
        this.icon = icon;
        this.questType = questType;
    }

    public Quest(String id, QuestType questType) {
        this.id = id;
        this.questType = questType;
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

    public Point getPoint() {
        return point;
    }

    public Task getTask() {
        return task;
    }

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public ItemType getIcon() {
        return icon;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void completeQuest(Player player) {
        rewards.forEach(reward -> reward.giveReward());
        player.sendMessage(Text.of(TextSerializers.FORMATTING_CODE.deserialize("&aYou have completed the " + getTitle() + " quest!")));
        QuestPlayer questPlayer = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        questPlayer.getQuestProgressMap().remove(id);
    }
}
