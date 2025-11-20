package com.wavebreaker.managers;

import java.util.Map;

public class LevelingManager {
    private static final int BASE_EXP_REQUIREMENT = 100;
    private static final double EXP_GROWTH_RATE = 1.2;

    public static int getExpRequirement(int level) {
        return BASE_EXP_REQUIREMENT * ((int) Math.pow(EXP_GROWTH_RATE, level - 1));
    }

    public static int getAllocatedStatPoints() {
        return 5;
    }

    public static Map<String, Integer> getBaseStatPoint() {
        return Map.of(
                "STR", 5,
                "VIT", 10);
    }
}