package com.wavebreaker.models;

import java.util.HashMap;
import java.util.Map;

import com.wavebreaker.managers.LevelingManager;
import com.wavebreaker.utils.Input;

public class Player extends Character {
    private int level;
    private int currentExp;

    private Skill attackSkill;
    private Skill healSkill;

    private Map<String, Integer> baseStats;
    private Map<String, Integer> allocatedStats;
    private int unusedStatPoints;

    public Player(String name) {
        super(name, 120, 12);

        this.level = 1;
        this.currentExp = 0;
        this.baseStats = LevelingManager.getBaseStatPoint();
        this.allocatedStats = new HashMap<String, Integer>();
        this.unusedStatPoints = 0;
        this.attackSkill = new Skill("Attack", 0);
        this.healSkill = new Skill("Heal", 4);

        this.recalculateStats();
    }

    @Override
    public void attack(Character target) {
        if (this.attackSkill.isReady()) {
            this.attackSkill.use();
            target.takeDamage(super.strength);
        }
    }

    public void heal() {
        if (this.healSkill.isReady()) {
            super.currentHP += calculateHealAmount();

            if (super.currentHP > super.maxHP) {
                super.currentHP = super.maxHP;
            }

            this.healSkill.use();
        }
    }

    @Override
    public void showInfo() {
        System.out.println("===== Player Info =====");
        System.out.println("Name: " + super.name);
        System.out.println("HP: " + super.currentHP + "/" + super.maxHP);
        System.out.println("Strength: " + super.strength);
        System.out.println("Level: " + this.level);
        System.out.println("EXP: " + this.currentExp + "/" + LevelingManager.getExpRequirement(this.level));

        this.showSkillInfo();
    }

    public void showSkillInfo() {
        System.out.println("===== Skill Info =====");
        if (this.attackSkill.isReady()) {
            System.out.println("[1] Serangan siap digunakan.");
        } else {
            System.out.println("[x] Cooldown Serangan: " + this.attackSkill.getCurrentCooldown());
        }

        if (this.healSkill.isReady()) {
            System.out.println("[2] Penyembuhan siap digunakan. " + calculateHealAmount() + " HP akan dipulihkan.");
        } else {
            System.out.println("[x] Cooldown Penyembuhan: " + this.healSkill.getCurrentCooldown());
        }
    }

    public void showStat() {
        System.out.println("===== Statistik =====");

        for (String stat : this.baseStats.keySet()) {
            System.out.println("- " + stat + " dasar: " + this.baseStats.get(stat));
        }

        System.out.println("---------------------");

        for (String stat : this.allocatedStats.keySet()) {
            System.out.println("- " + stat + " teralokasi: " + this.allocatedStats.get(stat));
        }

        System.out.println("Poin statistik yang belum digunakan: " + this.unusedStatPoints);
        System.out.println("Statistik yang dialokasikan: ");
    }

    public void gainExp(int expAmount) {
        this.currentExp += expAmount;
        this.checkLevelUp();
    }

    public void allocateStatPoint() {
        this.showStat();

        String statName = Input.get("Pilih statistik untuk dialokasikan atau batal (STR/VIT/n):");

        if (statName.equalsIgnoreCase("n")) {
            return;
        }

        if (statName == null || (!statName.equalsIgnoreCase("STR")
                && !statName.equalsIgnoreCase("VIT")
                && !statName.equalsIgnoreCase("n"))) {
            System.out.println("Input tidak dikenal, Ulangi lagi.");
            allocateStatPoint();

            return;
        }

        int points = Integer.parseInt(Input.get("Masukkan jumlah poin:"));

        if (points <= 0) {
            System.out.println("Jumlah poin harus lebih dari 0, ulangi lagi.");
            allocateStatPoint();

            return;
        }

        if (this.unusedStatPoints > 0) {
            statName = statName.toUpperCase();

            int currentAllocated = this.allocatedStats.getOrDefault(statName, 0);

            this.allocatedStats.put(statName, currentAllocated + points);
            this.unusedStatPoints -= points;

            recalculateStats();

            if (this.isPlayerHasUnusedStatPoints()) {
                allocateStatPoint();
            }

            return;
        }

        System.out.println("Poin statistik tidak mencukupi!");
    }

    public void decreaseSkillCooldown() {
        this.attackSkill.decreaseCooldown();
        this.healSkill.decreaseCooldown();
    }

    public void resetAllStates() {
        this.attackSkill.resetCooldown();
        this.healSkill.resetCooldown();
        super.setCurrentHP(super.maxHP);
    }

    public boolean isPlayerHasUnusedStatPoints() {
        return this.unusedStatPoints > 0;
    }

    private int calculateHealAmount() {
        return (int) (super.maxHP * 0.35);
    }

    private void checkLevelUp() {
        int expRequirement = LevelingManager.getExpRequirement(this.level);

        while (this.currentExp >= expRequirement) {
            processLevelUp(expRequirement);
            expRequirement = LevelingManager.getExpRequirement(this.level);
        }
    }

    private void processLevelUp(int expRequirement) {
        this.currentExp -= expRequirement;

        this.level++;

        Map<String, Integer> levelUpbaseStats = LevelingManager.getBaseStatPoint();

        for (String stat : levelUpbaseStats.keySet()) {
            int currentBase = this.baseStats.getOrDefault(stat, 0);
            this.baseStats.put(stat, currentBase + levelUpbaseStats.get(stat));
        }

        int allocatedPoints = LevelingManager.getAllocatedStatPoints();
        this.unusedStatPoints += allocatedPoints;

        System.out.println("Selamat! Anda naik level " + this.level + "!");
        System.out.println(allocatedPoints + " poin statistik telah ditambahkan!");

        this.recalculateStats();
    }

    private void recalculateStats() {
        int STR = getStat("STR");
        int VIT = getStat("VIT");

        super.setMaxHP(120 + (VIT * 8));
        super.setStrength(12 + (STR * 3));
    }

    private int getStat(String name) {
        return this.baseStats.getOrDefault(name, 0)
                + this.allocatedStats.getOrDefault(name, 0);
    }
}
