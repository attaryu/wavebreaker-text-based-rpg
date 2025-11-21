package com.wavebreaker.models;

import java.util.HashMap;
import java.util.Map;

import com.wavebreaker.config.Config;
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
        super(name, Config.PLAYER_BASE_HP, Config.PLAYER_BASE_STRENGTH);

        this.level = 1;
        this.currentExp = 0;
        this.baseStats = LevelingManager.getBaseStatPoint();
        this.allocatedStats = new HashMap<String, Integer>();

        this.allocatedStats.put("STR", 0);
        this.allocatedStats.put("VIT", 0);

        this.unusedStatPoints = 0;
        this.attackSkill = new Skill("Attack", Config.PLAYER_ATTACK_COOLDOWN);
        this.healSkill = new Skill("Heal", Config.PLAYER_HEAL_COOLDOWN);

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

        int point = Integer.parseInt(Input.get("Jumlah poin:"));

        if (point <= 0) {
            System.out.println("Jumlah poin harus lebih dari 0, ulangi lagi.");
            allocateStatPoint();

            return;
        }

        if (this.unusedStatPoints > 0) {
            statName = statName.toUpperCase();

            point = point > this.unusedStatPoints ? this.unusedStatPoints : point;

            int currentAllocated = this.allocatedStats.getOrDefault(statName, 0);

            this.allocatedStats.put(statName, currentAllocated + point);
            this.unusedStatPoints -= point;

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
        return (int) (super.maxHP * Config.PLAYER_HEAL_PERCENTAGE);
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

        super.setMaxHP(Config.PLAYER_BASE_HP + (VIT * Config.PLAYER_HP_PER_VIT));
        super.setStrength(Config.PLAYER_BASE_STRENGTH + (STR * Config.PLAYER_STRENGTH_PER_STR));
    }

    private int getStat(String name) {
        return this.baseStats.getOrDefault(name, 0)
                + this.allocatedStats.getOrDefault(name, 0);
    }
}
