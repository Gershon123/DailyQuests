package io.github.gershon.dailyquests.validation;

import com.pixelmonmod.pixelmon.enums.items.EnumApricorns;

import java.util.Arrays;

public class ApricornValidation {

    public static boolean isValidApricorn(String apricorn) {
        return Arrays.stream(EnumApricorns.values()).anyMatch(name -> name.toString().equals(apricorn));
    }
}
