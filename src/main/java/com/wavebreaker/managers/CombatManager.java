package com.wavebreaker.managers;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.Player;

public class CombatManager {
    private Player player;
    private Enemy enemy;

    public void startCombat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;

        this.player.resetAllStates();
    }

    public boolean isCombatOver() {
        return !(this.player.isAlive() && this.enemy.isAlive());
    }

    public boolean isPlayerWin() {
        return this.player.isAlive() && !this.enemy.isAlive();
    }

    public int getEnemyExpReward() {
        if (this.isPlayerWin()) {
            return this.enemy.getExpReward();
        }

        return 0;
    }

    public void processPlayerTurn(String action) {
        if (action.equalsIgnoreCase("1")) {
            this.player.attack(this.enemy);
        } else if (action.equalsIgnoreCase("2")) {
            this.player.heal();
        } else {
            System.out.println("Aksi tidak dikenal, giliran terlewat!");
        }

        if (this.enemy.isAlive()) {
            this.processEnemyTurn();
        }
    }

    private void processEnemyTurn() {
        this.enemy.attack(this.player);
        this.processRoundEnd();
    }

    private void processRoundEnd() {
        this.player.decreaseSkillCooldown();
        this.enemy.decreaseSkillCooldown();
    }
}