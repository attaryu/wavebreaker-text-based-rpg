/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.wavebreaker;
import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private List<Enemy> enemyTemplates;
    private int currentWave;

    public WaveManager() {
        this.enemyTemplates = new ArrayList<>();
        this.currentWave = 1;
        initTemplates();
    }

    // Membuat "Cetakan Kue" (Template Musuh)
    private void initTemplates() {
        // Nama, MaxHP, Strength, ExpReward
        enemyTemplates.add(new Enemy("Slime", 50, 5, 30));
        enemyTemplates.add(new Enemy("Goblin", 80, 8, 50));
    }

    // Mengambil musuh baru, meng-copy, dan menaikkan stat sesuai wave [cite: 65]
    public Enemy nextWave() {
        // Contoh sederhana: ambil template pertama lalu rotasi
        int index = (currentWave - 1) % enemyTemplates.size();
        Enemy template = enemyTemplates.get(index);
        
        Enemy newEnemy = template.copy(); // Menggunakan fitur clone
        newEnemy.scaleStats(currentWave); // Perkuat musuh
        
        System.out.println("\n=== WAVE " + currentWave + " DIMULAI! ===");
        System.out.println("Musuh muncul: " + newEnemy.getName() + " (HP: " + newEnemy.getCurrentHp() + ")");
        
        currentWave++;
        return newEnemy;
    }
}