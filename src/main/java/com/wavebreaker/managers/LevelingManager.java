package com.wavebreaker.managers;

import java.util.HashMap;
import java.util.Map;

public class LevelingManager {
    private static final int BASE_EXP_REQUIREMENT = 100;
    private static final double EXP_GROWTH_RATE = 1.25;

    public static int getExpRequirement(int level) {
        return (int) (BASE_EXP_REQUIREMENT * Math.pow(EXP_GROWTH_RATE, level - 1));
    }

    public static int getAllocatedStatPoints() {
        return 3;
    }

    public static Map<String, Integer> getBaseStatPoint() {
        HashMap<String, Integer> baseStats = new HashMap<>();

        baseStats.put("STR", 2);
        baseStats.put("VIT", 3);

        return baseStats;
    }
}