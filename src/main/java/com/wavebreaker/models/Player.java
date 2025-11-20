package com.wavebreaker.models;

import java.util.Map;

import com.wavebreaker.managers.LevelingManager;

public class Player extends Character {
    private int level;
    private int currentExp;

    private Skill attackSkill;
    private Skill healSkill;

    private Map<String, Integer> baseStats;
    private Map<String, Integer> allocatedStats;
    private int unusedStatPoints;

    public Player(String name) {
        super(name, 100, 10);
        this.level = 1;
        this.currentExp = 0;

        this.baseStats = LevelingManager.getBaseStatPoint();
        this.recalculateStats();

        this.attackSkill = new Skill("Attack", 1);
        this.healSkill = new Skill("Heal", 3);
    }

    @Override
    public void attack(Character target) {
        if (this.attackSkill.isReady()) {
            target.takeDamage(super.strength);
            this.attackSkill.use();
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
    public void getInfo() {
        System.out.println("===== Player Info =====");
        System.out.println("Name: " + super.name);
        System.out.println("HP: " + super.currentHP + "/" + super.maxHP);
        System.out.println("Strength: " + super.strength);
        System.out.println("Level: " + this.level);
        System.out.println("EXP: " + this.currentExp + "/" + LevelingManager.getExpRequirement(this.level));
        System.out.println("===== Skill Info =====");
        System.out.println("Attack Cooldown: " + this.attackSkill.getCurrentCooldown());
        System.out.println("Heal amount: " + calculateHealAmount());
        System.out.println("Heal Cooldown: " + this.healSkill.getCurrentCooldown());
    }

    public void gainExp(int expAmount) {
        this.currentExp += expAmount;
        this.checkLevelUp();
    }

    public void allocateStatPoint(String statName, int points) {
        if (this.unusedStatPoints > 0 && points <= this.unusedStatPoints) {
            int currentAllocated = this.allocatedStats.getOrDefault(statName, 0);

            this.allocatedStats.put(statName, currentAllocated + points);
            this.unusedStatPoints -= points;

            recalculateStats();
        }
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

    private int calculateHealAmount() {
        return super.maxHP / 5;
    }

    private void checkLevelUp() {
        int expRequirement = LevelingManager.getExpRequirement(this.level);

        while (this.currentExp >= expRequirement) {
            processLevelUp();
            expRequirement = LevelingManager.getExpRequirement(this.level);
        }
    }

    private void processLevelUp() {
        this.currentExp -= LevelingManager.getExpRequirement(this.level);
        this.level++;

        Map<String, Integer> levelUpbaseStats = LevelingManager.getBaseStatPoint();

        for (String stat : levelUpbaseStats.keySet()) {
            int currentBase = this.baseStats.getOrDefault(stat, 0);
            this.baseStats.put(stat, currentBase + levelUpbaseStats.get(stat));
        }

        this.unusedStatPoints += LevelingManager.getAllocatedStatPoints();
        this.recalculateStats();
    }

    private void recalculateStats() {
        int STR = getStat("STR");
        int VIT = getStat("VIT");

        super.setMaxHP(100 + (VIT * 10));
        super.setStrength(10 + (STR * 2));
    }

    private int getStat(String name) {
        return this.baseStats.getOrDefault(name, 0)
                + this.allocatedStats.getOrDefault(name, 0);
    }
}
