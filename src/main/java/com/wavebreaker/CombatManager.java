package com.wavebreaker;

public class CombatManager {
    private Player player;
    private Enemy currentEnemy;

    public void startCombat(Player p, Enemy e) {
        this.player = p;
        this.currentEnemy = e;
    }

    // Mengecek apakah pertarungan selesai [cite: 9]
    public boolean isCombatOver() {
        if (!currentEnemy.isAlive()) {
            System.out.println("MUSUH KALAH!");
            return true;
        }
        if (!player.isAlive()) {
            System.out.println("GAME OVER! Anda Kalah.");
            System.exit(0); // Keluar program
        }
        return false;
    }

    // Logika Giliran Player [cite: 22-26]
    public void processPlayerTurn(String action) {
        if (action.equalsIgnoreCase("1")) {
            player.attack(currentEnemy);
        } else if (action.equalsIgnoreCase("2")) {
            player.heal();
        } else {
            System.out.println("Aksi tidak dikenal, giliran terlewat!");
        }

        // Cek apakah musuh mati setelah diserang [cite: 27]
        if (currentEnemy.isAlive()) {
            processEnemyTurn(); // Jika hidup, musuh membalas [cite: 28-29]
        }
        
        processRoundEnd(); // Pembersihan akhir ronde [cite: 31]
    }

    // Logika Giliran Musuh [cite: 29]
    private void processEnemyTurn() {
        currentEnemy.attack(player);
    }

    // Akhir Ronde & Cooldown [cite: 33-34]
    private void processRoundEnd() {
        player.decreaseSkillCooldown();
        // enemy.decreaseSkillCooldown(); (jika ada)
        System.out.println("--- Akhir Ronde (HP Player: " + player.getCurrentHp() + ") ---");
    }
    
    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }
}