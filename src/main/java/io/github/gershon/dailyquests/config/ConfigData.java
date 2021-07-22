package io.github.gershon.dailyquests.config;

public class ConfigData {

    private String questComplete;

    public ConfigData(String questComplete) {
        this.questComplete = questComplete;
    }

    public String getQuestComplete() {
        return questComplete;
    }
}
