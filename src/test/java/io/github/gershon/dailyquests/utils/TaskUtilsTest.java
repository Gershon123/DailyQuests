package io.github.gershon.dailyquests.utils;

import io.github.gershon.dailyquests.utils.TaskUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskUtilsTest {

    @Test
    void testIsValidTask() {
        Assertions.assertFalse(TaskUtils.isValidTask(null));
        Assertions.assertFalse(TaskUtils.isValidTask("harvest_apricorn"));
        Assertions.assertFalse(TaskUtils.isValidTask("invalid"));
        Assertions.assertTrue(TaskUtils.isValidTask("HARVEST_APRICORN"));
        Assertions.assertTrue(TaskUtils.isValidTask("CRAFT_ITEM"));
        Assertions.assertTrue(TaskUtils.isValidTask("DEFEAT_POKEMON"));
    }
}