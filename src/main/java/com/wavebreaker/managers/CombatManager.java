package com.wavebreaker.managers;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.Player;
import com.wavebreaker.utils.Delay;
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
        System.out.println("\n═════ RONDE " + this.round + " ═════");

        this.enemy.showInfo();
        this.player.showInfo();
        this.player.showSkillInfo();

        String action = Input.get("\nPilih: ");

        System.out.println();
        if (action.equals("1")) {
            this.player.attack(this.enemy);
        } else if (action.equals("2")) {
            this.player.heal();
        } else {
            System.out.println("Aksi tidak valid!");
        }

        Delay.wait(400);

        if (this.enemy.isAlive()) {
            this.processEnemyTurn();
        }
    }

    private void processEnemyTurn() {
        System.out.println("\n═════ Giliran Musuh ═════");
        Delay.wait(300);
        this.enemy.attack(this.player);
        Delay.wait(400);
        this.processRoundEnd();
    }

    private void processRoundEnd() {
        this.player.decreaseSkillCooldown();
        this.enemy.decreaseSkillCooldown();
    }
}