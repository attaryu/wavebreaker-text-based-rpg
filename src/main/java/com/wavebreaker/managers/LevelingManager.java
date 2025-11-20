package com.wavebreaker.managers;

public class LevelingManager {

    // Private constructor agar tidak bisa diinstansiasi (karena utility class)
    private LevelingManager() {
    }

    // Menjawab pertanyaan: "Level X butuh berapa EXP?"
    public static int getExpRequirement(int level) {
        // Contoh rumus sederhana: Level 1 butuh 100, Level 2 butuh 200, dst.
        return level * 100;
    }

    // Contoh rumus scaling stat (opsional, untuk menunjang fitur level up)
    public static int calculateNewMaxHp(int level) {
        return 100 + (level * 20);
    }

    public static int calculateNewStrength(int level) {
        return 10 + (level * 5);
    }
}