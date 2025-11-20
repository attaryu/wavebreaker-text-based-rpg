package com.wavebreaker.models;

public class Skill {
    private final String name;
    private final int BASE_COOLDOWN;
    private int currentCooldown;

    public Skill(String name, int baseCooldown) {
        this.name = name;
        this.BASE_COOLDOWN = baseCooldown;
        this.currentCooldown = 0;
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }

    public void use() {
        this.currentCooldown = BASE_COOLDOWN;
    }

    public void decreaseCooldown() {
        if (this.currentCooldown > 0) {
            this.currentCooldown--;
        }
    }

    public String getName() {
        return name;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void resetCooldown() {
        this.currentCooldown = 0;
    }
}