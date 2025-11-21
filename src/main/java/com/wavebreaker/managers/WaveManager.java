package com.wavebreaker.managers;

import java.util.ArrayList;
import java.util.List;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.EnemyType;

public class WaveManager {
    private final int MAX_WAVES = 30;
    private final double HP_MULTIPLIER = 1.1;
    private final double STRENGTH_MULTIPLIER = 1.06;
    private final double EXP_MULTIPLIER = 1.18;

    private final List<Enemy> normalEnemies = new ArrayList<Enemy>();
    private final List<Enemy> eliteEnemies = new ArrayList<Enemy>();
    private final List<Enemy> bossEnemies = new ArrayList<Enemy>();

    private int currentWave = 0;

    public WaveManager() {
        this.normalEnemies.add(new Enemy("Goblin", 80, 10, 60, EnemyType.NORMAL));
        this.normalEnemies.add(new Enemy("Orc", 110, 13, 70, EnemyType.NORMAL));

        this.eliteEnemies.add(new Enemy("Troll", 180, 18, 140, EnemyType.ELITE));
        this.eliteEnemies.add(new Enemy("Dark Knight", 220, 21, 160, EnemyType.ELITE));

        this.bossEnemies.add(new Enemy("Dragon", 380, 28, 300, EnemyType.BOSS));
        this.bossEnemies.add(new Enemy("Demon Lord", 420, 32, 330, EnemyType.BOSS));
    }

    public Enemy nextWave() {
        this.currentWave++;

        System.out.println("\n═════ WAVE " + this.currentWave + "/" + this.MAX_WAVES + " ═════");

        if (this.currentWave % 10 == 0) {
            return this.scaleEnemy(this.spawnBossEnemy());
        } else if (this.currentWave % 5 == 0) {
            return this.scaleEnemy(this.spawnEliteEnemy());
        } else {
            return this.scaleEnemy(this.spawnNormalEnemy());
        }
    }

    public boolean isWavesEnded() {
        return this.currentWave >= this.MAX_WAVES;
    }

    public int getCurrentWave() {
        return this.currentWave;
    }

    private Enemy scaleEnemy(Enemy enemy) {
        int scaledHP = (int) (enemy.getMaxHP() * Math.pow(HP_MULTIPLIER, currentWave - 1));
        int scaledStrength = (int) (enemy.getStrength() * Math.pow(STRENGTH_MULTIPLIER, currentWave - 1));
        int scaledExp = (int) (enemy.getExpReward() * Math.pow(EXP_MULTIPLIER, currentWave - 1));

        return enemy.copy(scaledStrength, scaledHP, scaledExp);
    }

    private Enemy spawnNormalEnemy() {
        return this.getRandomEnemy(normalEnemies);
    }

    private Enemy spawnEliteEnemy() {
        System.out.println("ELITE ENEMY!");
        return this.getRandomEnemy(eliteEnemies);
    }

    private Enemy spawnBossEnemy() {
        System.out.println("BOSS FIGHT!");
        return this.getRandomEnemy(bossEnemies);
    }

    private Enemy getRandomEnemy(List<Enemy> enemies) {
        return enemies.get((int) (Math.random() * enemies.size()));
    }
}