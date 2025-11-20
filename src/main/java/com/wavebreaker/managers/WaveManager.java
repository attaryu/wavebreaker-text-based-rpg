package com.wavebreaker.managers;

import java.util.ArrayList;
import java.util.List;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.EnemyType;

public class WaveManager {
    private final int MAX_WAVES = 10;
    private final double HP_MULTIPLIER = 1.5;
    private final double STRENGTH_MULTIPLIER = 1.2;
    private final double EXP_MULTIPLIER = 1.3;

    private final List<Enemy> normalEnemies = new ArrayList<Enemy>();
    private final List<Enemy> eliteEnemies = new ArrayList<Enemy>();
    private final List<Enemy> bossEnemies = new ArrayList<Enemy>();

    private int currentWave = 1;

    public WaveManager() {
        this.normalEnemies.add(new Enemy("Goblin", 100, 10, 20, EnemyType.NORMAL));
        this.normalEnemies.add(new Enemy("Orc", 150, 15, 30, EnemyType.NORMAL));

        this.eliteEnemies.add(new Enemy("Troll", 300, 25, 50, EnemyType.ELITE));
        this.eliteEnemies.add(new Enemy("Dark Knight", 400, 30, 70, EnemyType.ELITE));

        this.bossEnemies.add(new Enemy("Dragon", 1000, 50, 200, EnemyType.BOSS));
        this.bossEnemies.add(new Enemy("Demon Lord", 1200, 60, 250, EnemyType.BOSS));
    }

    public Enemy nextWave() {
        this.currentWave++;

        if (this.currentWave % 10 == 0) {
            return this.scaleEnemy(this.spawnBossEnemy());
        } else if (this.currentWave % 5 == 0) {
            return this.scaleEnemy(this.spawnEliteEnemy());
        } else {
            return this.scaleEnemy(this.spawnNormalEnemy());
        }
    }

    public boolean isMaxWaveReached() {
        return this.currentWave >= this.MAX_WAVES;
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
        return this.getRandomEnemy(eliteEnemies);
    }

    private Enemy spawnBossEnemy() {
        return this.getRandomEnemy(bossEnemies);
    }

    private Enemy getRandomEnemy(List<Enemy> enemies) {
        return enemies.get((int) (Math.random() * enemies.size()));
    }
}