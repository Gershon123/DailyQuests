package io.github.gershon.dailyquests.listeners;

import com.pixelmonmod.pixelmon.api.events.ApricornEvent;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import io.github.gershon.dailyquests.DailyQuests;
import io.github.gershon.dailyquests.quests.Quest;
import io.github.gershon.dailyquests.quests.tasks.TaskType;
import io.github.gershon.dailyquests.utils.QuestUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HarvestApricornListener {

    @SubscribeEvent
    public void harvestApricorn(ApricornEvent.PickApricorn event) {
        Player player = (Player) event.player;
        List<Quest> quests = DailyQuests.getInstance().playerMap.get(player.getUniqueId());
        if (quests != null && quests.size() > 0) {
            List<Quest> apricornQuests = QuestUtils.getQuestsForTask(quests, TaskType.HARVEST_APRICORN);
            int amountPicked = event.getPickedStack().getCount();
            QuestUtils.handleQuestsTaskUpdate(apricornQuests, amountPicked, player);
        }
    }

}
