package com.wavebreaker.models;

public abstract class Character {
    protected String name;
    protected int maxHP;
    protected int currentHp;
    protected int strength;

    public Character(String name, int maxHP, int strength) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHp = maxHP;
        this.strength = strength;
    }

    public void takeDamage(int damage) {
        this.currentHp -= damage;

        if (this.currentHp < 0) {
            this.currentHp = 0;
        }

        System.out.println(this.name + " terkena " + damage + " damage. Sisa HP: " + this.currentHp);
    }

    public abstract void attack(Character target);
    public abstract void getInfo();

    public boolean isAlive() {
        return this.currentHp > 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHP;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getStrength() {
        return strength;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}