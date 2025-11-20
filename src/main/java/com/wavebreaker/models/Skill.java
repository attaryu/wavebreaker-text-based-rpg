package com.wavebreaker.models;

public class Skill {
    private String name;
    private int maxCooldown;
    private int currentCooldown;

    public Skill(String name, int maxCooldown) {
        this.name = name;
        this.maxCooldown = maxCooldown;
        this.currentCooldown = 0; // Siap digunakan saat awal
    }

    public String getName() {
        return name;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    // Digunakan saat skill dipakai
    public void startCooldown() {
        this.currentCooldown = maxCooldown;
    }

    // Logika penurunan cooldown (dipanggil oleh Player/Enemy saat fase Cooldown)
    public void decreaseCooldown() {
        if (this.currentCooldown > 0) {
            this.currentCooldown--;
        }
    }

    public boolean isReady() {
        return currentCooldown == 0;
    }
}