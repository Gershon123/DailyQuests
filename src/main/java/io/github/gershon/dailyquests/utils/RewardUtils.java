package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.quests.rewards.RewardType;

import java.util.Arrays;

public class RewardUtils {

    public static boolean isValidReward(String reward) {
        return Arrays.stream(RewardType.values()).anyMatch(name -> name.toString().equals(reward));
    }
}
