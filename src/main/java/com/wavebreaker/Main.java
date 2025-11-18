package com.wavebreaker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Inisiasi [cite: 6]
        System.out.println("=== RPG GAME START ===");
        Player player = new Player("Hero");
        
        // Menambahkan skill contoh ke player
        player.addSkill(new Skill("Fireball", 3));

        WaveManager waveManager = new WaveManager();
        CombatManager combatManager = new CombatManager();

        // Setup musuh pertama
        Enemy firstEnemy = waveManager.nextWave();
        combatManager.startCombat(player, firstEnemy);

        // 2. Mulai Game Loop 
        while (player.isAlive()) {
            
            // 3. Cek Status Combat [cite: 9]
            if (combatManager.isCombatOver()) {
                // 4. Jika Menang: Beri XP & Spawn Musuh Baru [cite: 10-17]
                Enemy defeatedEnemy = combatManager.getCurrentEnemy();
                player.gainExp(defeatedEnemy.getExpReward());
                
                System.out.println("Tekan Enter untuk lanjut ke wave berikutnya...");
                scanner.nextLine();

                Enemy nextEnemy = waveManager.nextWave();
                combatManager.startCombat(player, nextEnemy);
            } else {
                // 5. Jika Bertarung: Tunggu Input Player [cite: 19-21]
                System.out.println("\nGiliran Anda:");
                System.out.println("1. Attack");
                System.out.println("2. Heal (Skill)");
                System.out.print("Pilih aksi: ");
                
                String input = scanner.nextLine();
                
                // Kirim input ke Wasit
                combatManager.processPlayerTurn(input);
            }
        }
        
        scanner.close();
    }
}
