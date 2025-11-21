package com.wavebreaker.models;

public class Skill {
    private final int BASE_COOLDOWN;
    private int currentCooldown;

    public Skill(String name, int baseCooldown) {
        this.BASE_COOLDOWN = baseCooldown;
        this.currentCooldown = 0;
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public void use() {
        this.currentCooldown = this.BASE_COOLDOWN + 1;
    }

    public void decreaseCooldown() {
        if (this.currentCooldown > 0) {
            this.currentCooldown--;
        }
    }

    public int getCurrentCooldown() {
        return this.currentCooldown;
    }

    public void resetCooldown() {
        this.currentCooldown = 0;
    }
}