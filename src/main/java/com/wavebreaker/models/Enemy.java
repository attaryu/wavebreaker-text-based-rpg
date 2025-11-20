package com.wavebreaker.models;

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
                this.attackSkill = new Skill("Basic attack", 3);
                break;

            case EnemyType.ELITE:
                this.attackSkill = new Skill("Basic attack", 1);
                break;

            default:
                this.attackSkill = new Skill("Basic attack", 0);
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
        System.out.println("===== Enemy Info =====");
        System.out.println("Name: " + this.name);
        System.out.println("Type: " + this.enemyType);
        System.out.println("HP: " + super.currentHP + "/" + super.maxHP);
        System.out.println("Strength: " + this.strength);
        System.out.println("EXP Reward: " + this.expReward);
        System.out.println("===== Skill Info =====");
        System.out.println("Attack Cooldown: " + this.attackSkill.getCurrentCooldown());
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
