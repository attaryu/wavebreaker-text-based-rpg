package com.wavebreaker.models;

public abstract class Character {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Character(String name, int maxHp, int strength) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
    }

    // Method dasar untuk combat
    public void takeDamage(int damage) {
        this.currentHp -= damage;
        if (this.currentHp < 0)
            this.currentHp = 0;
        System.out.println(this.name + " terkena " + damage + " damage. Sisa HP: " + this.currentHp);
    }

    public boolean isAlive() {
        return this.currentHp > 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    // Abstract method agar Player dan Enemy mengimplementasikan cara serang
    // masing-masing
    public abstract void attack(Character target);
}