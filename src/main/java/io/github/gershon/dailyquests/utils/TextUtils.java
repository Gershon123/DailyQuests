package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.player.QuestPlayer;
import io.github.gershon.dailyquests.player.QuestProgress;
import io.github.gershon.dailyquests.quests.Quest;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {
    private static final String[] progress_colors = {"&4", "&c", "&6", "&e", "&2", "&a"};
    private static final String progress_bar = "::::::::::::::::::::";

    public static Text getText(String text) {
        return Text.of(TextSerializers.FORMATTING_CODE.deserialize(text));
    }

    public static String getQuestProgress(Quest quest, QuestPlayer questPlayer) {
        QuestProgress questProgress = questPlayer.getQuestProgressMap().get(quest.getId());
        return "&7("
                + (questProgress != null ? questProgress.getTaskAmount() : 0)
                + "/"
                + quest.getTask().getTotalAmount()
                + ")";
    }

    public static Text progressBar(int current, int total, String append) {
        if (total <= 0) {
            total = 1;
            current = 0;
        }
        current = Math.max(Math.min(current, total), 0);

        int length = (current * 20) / total;
        if (append == null)
            append = ((current * 100) / total) + "%";

        return getText(progress_colors[(current * 5) / total]
                + progress_bar.substring(20 - length)
                + "&7"
                + progress_bar.substring(length) +
                " - " +
                append);
    }
}
