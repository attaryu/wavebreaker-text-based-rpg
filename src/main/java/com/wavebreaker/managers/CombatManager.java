package com.wavebreaker.managers;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.Player;
import com.wavebreaker.utils.Input;

public class CombatManager {
    private Player player;
    private Enemy enemy;
    private int round = 0;

    public void startCombat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.round = 0;

        this.player.resetAllStates();
    }

    public boolean isCombatOver() {
        return !(this.player.isAlive() && this.enemy.isAlive());
    }

    public void processPlayerTurn() {
        this.round++;
        System.out.println("Ronde " + this.round + "!");

        this.enemy.showInfo();
        this.player.showInfo();

        String action = Input.get("Giliran Anda! Pilih aksi: ");

        if (action.equals("1")) {
            this.player.attack(this.enemy);
        } else if (action.equals("2")) {
            this.player.heal();
        } else {
            System.out.println("Aksi tidak dikenal, giliran terlewat!");
        }

        if (this.enemy.isAlive()) {
            this.processEnemyTurn();
        }
    }

    private void processEnemyTurn() {
        System.out.println("Giliran musuh!");
        this.enemy.attack(this.player);
        this.processRoundEnd();
    }

    private void processRoundEnd() {
        this.player.decreaseSkillCooldown();
        this.enemy.decreaseSkillCooldown();
    }
}