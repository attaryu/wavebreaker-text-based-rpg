package com.wavebreaker.managers;

import java.util.List;

import com.wavebreaker.config.Config;
import com.wavebreaker.models.Enemy;

public class WaveManager {
    private int currentWave = Config.INITIAL_WAVE - 1;

    public Enemy nextWave() {
        this.currentWave++;

        System.out.println("\n═════ WAVE " + this.currentWave + "/" + Config.MAX_WAVES + " ═════");

        if (this.currentWave % 10 == 0) {
            return this.scaleEnemy(this.spawnBossEnemy());
        } else if (this.currentWave % 5 == 0) {
            return this.scaleEnemy(this.spawnEliteEnemy());
        } else {
            return this.scaleEnemy(this.spawnNormalEnemy());
        }
    }

    public boolean isWavesEnded() {
        return this.currentWave >= Config.MAX_WAVES;
    }

    public int getCurrentWave() {
        return this.currentWave;
    }

    private Enemy scaleEnemy(Enemy enemy) {
        int scaledHP = (int) (enemy.getMaxHP() * Math.pow(Config.ENEMY_HP_MULTIPLIER, currentWave - 1));
        int scaledStrength = (int) (enemy.getStrength() * Math.pow(Config.ENEMY_STRENGTH_MULTIPLIER, currentWave - 1));
        int scaledExp = (int) (enemy.getExpReward() * Math.pow(Config.ENEMY_EXP_MULTIPLIER, currentWave - 1));

        return enemy.copy(scaledStrength, scaledHP, scaledExp);
    }

    private Enemy spawnNormalEnemy() {
        return this.getRandomEnemy(Config.NORMAL_ENEMIES);
    }

    private Enemy spawnEliteEnemy() {
        System.out.println("ELITE ENEMY!");
        return this.getRandomEnemy(Config.ELITE_ENEMIES);
    }

    private Enemy spawnBossEnemy() {
        System.out.println("BOSS FIGHT!");
        return this.getRandomEnemy(Config.BOSS_ENEMIES);
    }

    private Enemy getRandomEnemy(List<Enemy> enemies) {
        return enemies.get((int) (Math.random() * enemies.size()));
    }
}