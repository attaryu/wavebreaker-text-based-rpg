package com.wavebreaker.models;

import com.wavebreaker.config.Config;

public class Enemy extends Character {
    private int expReward;
    private EnemyType enemyType;
    private Skill attackSkill;

    public Enemy(String name, int maxHP, int strength, int expReward, EnemyType enemyType) {
        super(name, maxHP, strength);
        this.expReward = expReward;
        this.enemyType = enemyType;

        switch (enemyType) {
            case EnemyType.BOSS:
                this.attackSkill = new Skill("Basic attack", Config.BOSS_ENEMY_ATTACK_COOLDOWN);
                break;

            case EnemyType.ELITE:
                this.attackSkill = new Skill("Basic attack", Config.ELITE_ENEMY_ATTACK_COOLDOWN);
                break;

            default:
                this.attackSkill = new Skill("Basic attack", Config.NORMAL_ENEMY_ATTACK_COOLDOWN);
        }
    }

    @Override
    public void attack(Character target) {
        if (this.attackSkill.isReady()) {
            this.attackSkill.use();
            target.takeDamage(this.strength);
        }
    }

    @Override
    public void showInfo() {
        String cdInfo = this.attackSkill.getCurrentCooldown() > 0
                ? " (CD: " + this.attackSkill.getCurrentCooldown() + " ronde)"
                : "";

        System.out.println("\n[" + super.name + "] " + this.enemyType + " | HP: " + super.currentHP + "/" + super.maxHP
                + " | ATK: " + super.strength + cdInfo + " | EXP reward: " + this.expReward);
    }

    public void decreaseSkillCooldown() {
        this.attackSkill.decreaseCooldown();
    }

    public Enemy copy(int newStrength, int newMaxHp, int newExpReward) {
        return new Enemy(this.name, newMaxHp, newStrength, newExpReward, this.enemyType);
    }

    public int getExpReward() {
        return expReward;
    }
}
