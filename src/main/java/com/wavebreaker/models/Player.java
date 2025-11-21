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
        super(name, 150, 15);

        this.level = 1;
        this.currentExp = 0;
        this.baseStats = LevelingManager.getBaseStatPoint();
        this.allocatedStats = new HashMap<String, Integer>();

        this.allocatedStats.put("STR", 0);
        this.allocatedStats.put("VIT", 0);

        this.unusedStatPoints = 0;
        this.attackSkill = new Skill("Attack", 0);
        this.healSkill = new Skill("Heal", 3);

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
            int healAmount = calculateHealAmount();
            super.currentHP += healAmount;

            if (super.currentHP > super.maxHP) {
                super.currentHP = super.maxHP;
            }

            System.out.println(
                    "-> " + super.name + " +" + healAmount + " HP (" + super.currentHP + "/" + super.maxHP + ")");
            this.healSkill.use();
        } else {
            System.out.println("Heal masih cooldown!");
        }
    }

    @Override
    public void showInfo() {
        System.out.println("\n[" + super.name + "] Lv." + this.level + " | HP: " + super.currentHP + "/" + super.maxHP
                + " | ATK: " + super.strength + " | EXP: " + this.currentExp + "/"
                + LevelingManager.getExpRequirement(this.level));
    }

    public void showSkillInfo() {
        System.out.println("\n═════ Aksi ═════");
        if (this.attackSkill.isReady()) {
            System.out.println("[1] Attack (DMG: " + super.strength + ")");
        } else {
            System.out.println("[x] Attack (CD: " + this.attackSkill.getCurrentCooldown() + ")");
        }

        if (this.healSkill.isReady()) {
            System.out.println("[2] Heal (+" + calculateHealAmount() + " HP)");
        } else {
            System.out.println("[x] Heal (CD: " + this.healSkill.getCurrentCooldown() + " ronde)");
        }
    }

    public void showStat() {
        System.out.println("\n═════ Statistik ═════");

        for (String stat : this.baseStats.keySet()) {
            System.out.println("- " + stat + " dasar: " + this.baseStats.get(stat));
        }

        for (String stat : this.allocatedStats.keySet()) {
            System.out.println("- " + stat + " extra: " + this.allocatedStats.get(stat));
        }

        System.out.println("Sisa poin statistik: " + this.unusedStatPoints);
    }

    public void gainExp(int expAmount) {
        this.currentExp += expAmount;
        this.checkLevelUp();
    }

    public void allocateStatPoint() {
        String statName = Input.get("Pilih statistik atau batal (STR/VIT/n):");

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

        int points = Integer.parseInt(Input.get("Jumlah poin:"));

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
            this.showStat();

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
        return (int) (super.maxHP * 0.40);
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

        System.out.println("\nLEVEL UP! Lv." + this.level + " (+" + allocatedPoints + " stat points)");

        this.recalculateStats();
    }

    private void recalculateStats() {
        int STR = getStat("STR");
        int VIT = getStat("VIT");

        super.setMaxHP(150 + (VIT * 9));
        super.setStrength(15 + (STR * 4));
    }

    private int getStat(String name) {
        return this.baseStats.getOrDefault(name, 0)
                + this.allocatedStats.getOrDefault(name, 0);
    }
}
