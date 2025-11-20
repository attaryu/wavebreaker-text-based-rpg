package com.wavebreaker.models;

public abstract class Character {
    protected String name;
    protected int maxHP;
    protected int currentHP;
    protected int strength;

    public Character(String name, int maxHP, int strength) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.strength = strength;
    }

    public void takeDamage(int damage) {
        this.currentHP -= damage;

        if (this.currentHP < 0) {
            this.currentHP = 0;
        }

        System.out.println(this.name + " terkena " + damage + " damage. Sisa HP: " + this.currentHP);
    }

    public abstract void attack(Character target);
    public abstract void showInfo();

    public boolean isAlive() {
        return this.currentHP > 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getStrength() {
        return strength;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}