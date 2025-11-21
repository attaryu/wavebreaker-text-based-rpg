package com.wavebreaker.managers;

import java.util.HashMap;
import java.util.Map;

import com.wavebreaker.config.Config;

public class LevelingManager {
    public static int getExpRequirement(int level) {
        return (int) (Config.BASE_EXP_REQUIREMENT * Math.pow(Config.EXP_GROWTH_RATE, level - 1));
    }

    public static int getAllocatedStatPoints() {
        return Config.ALLOCATED_STAT_POINTS_PER_LEVEL;
    }

    public static Map<String, Integer> getBaseStatPoint() {
        HashMap<String, Integer> baseStats = new HashMap<>();

        baseStats.put("STR", Config.BASE_STR_PER_LEVEL);
        baseStats.put("VIT", Config.BASE_VIT_PER_LEVEL);

        return baseStats;
    }
}