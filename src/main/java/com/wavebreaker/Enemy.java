package com.wavebreaker;

public class Enemy extends Character {
    private int expReward;

    public Enemy(String name, int maxHp, int strength, int expReward) {
        super(name, maxHp, strength);
        this.expReward = expReward;
    }

    // Getter untuk ExpReward yang akan diambil oleh GameSystem [cite: 13]
    public int getExpReward() {
        return expReward;
    }

    @Override
    public void attack(Character target) {
        System.out.println("Musuh " + this.name + " menyerang balik!");
        target.takeDamage(this.strength);
    }

    // Resep Kloning / Prototype Pattern [cite: 57]
    // Digunakan oleh WaveManager.nextWave() [cite: 15, 65]
    public Enemy copy() {
        // Membuat object baru dengan stat yang sama (sebagai template dasar)
        return new Enemy(this.name, this.maxHp, this.strength, this.expReward);
    }
    
    // Method untuk scaling musuh (biasanya dipanggil WaveManager setelah copy) [cite: 65]
    public void scaleStats(int waveNumber) {
        this.maxHp += (waveNumber * 10);
        this.strength += waveNumber;
        this.currentHp = this.maxHp; // Reset HP full untuk musuh baru
    }

    // Alur Cooldown untuk musuh (jika musuh punya skill) [cite: 34]
    public void decreaseSkillCooldown() {
        // Implementasi jika musuh punya skill list
    }
}
