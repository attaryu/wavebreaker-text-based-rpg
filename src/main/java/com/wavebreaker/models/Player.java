package com.wavebreaker.models;

import java.util.ArrayList;
import java.util.List;

import com.wavebreaker.managers.LevelingManager;

public class Player extends Character {
    private int level;
    private int currentExp;
    private List<Skill> skills;

    public Player(String name) {
        super(name, 100, 10); // Stat awal
        this.level = 1;
        this.currentExp = 0;
        this.skills = new ArrayList<>();
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    @Override
    public void attack(Character target) {
        System.out.println(this.name + " menyerang " + target.getName() + "!");
        target.takeDamage(this.strength);
    }

    // Logika Heal ada di Player, bukan di class Skill [cite: 75-76]
    public void heal() {
        int healAmount = 20; // Contoh nilai heal
        this.currentHp += healAmount;
        if (this.currentHp > this.maxHp)
            this.currentHp = this.maxHp;
        System.out.println(this.name + " memulihkan diri sebesar " + healAmount + " HP.");
    }

    // Dipanggil oleh GameSystem saat musuh kalah [cite: 13]
    public void gainExp(int exp) {
        this.currentExp += exp;
        System.out.println("Mendapatkan " + exp + " EXP.");
        checkLevelUp();
    }

    // Menggunakan LevelingManager untuk cek syarat level up
    private void checkLevelUp() {
        int reqExp = LevelingManager.getExpRequirement(this.level);
        while (this.currentExp >= reqExp) {
            levelUp();
            reqExp = LevelingManager.getExpRequirement(this.level);
        }
    }

    private void levelUp() {
        this.currentExp -= LevelingManager.getExpRequirement(this.level);
        this.level++;

        // Update stat saat level up
        this.maxHp = LevelingManager.calculateNewMaxHp(this.level);
        this.strength = LevelingManager.calculateNewStrength(this.level);
        this.currentHp = this.maxHp; // Full heal saat level up

        System.out.println("LEVEL UP! Sekarang level " + this.level);
    }

    // Bagian dari Alur Cooldown (Langkah 10 & 11) [cite: 34]
    public void decreaseSkillCooldown() {
        for (Skill s : skills) {
            s.decreaseCooldown();
        }
    }
}
