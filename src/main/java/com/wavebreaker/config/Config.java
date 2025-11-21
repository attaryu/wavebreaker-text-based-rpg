package com.wavebreaker.config;

import java.util.ArrayList;
import java.util.List;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.EnemyType;

public class Config {
  // Game Settings
  public static final int INITIAL_WAVE = 1;
  public static final int MAX_WAVES = 30;

  // Player Base Stats
  public static final int PLAYER_BASE_HP = 200;     
  public static final int PLAYER_BASE_STRENGTH = 20; 
  
  // Cooldown Management
  public static final int PLAYER_ATTACK_COOLDOWN = 0;
  public static final int PLAYER_HEAL_COOLDOWN = 4;
  public static final double PLAYER_HEAL_PERCENTAGE = 0.30;

  // Player Stat Scaling
  public static final int PLAYER_HP_PER_VIT = 15;
  public static final int PLAYER_STRENGTH_PER_STR = 3;

  // Leveling System
  public static final int BASE_EXP_REQUIREMENT = 100;
  public static final double EXP_GROWTH_RATE = 1.25; 
  public static final int ALLOCATED_STAT_POINTS_PER_LEVEL = 3;
  
  // Auto-scaling level up (Bonus kecil)
  public static final int BASE_STR_PER_LEVEL = 1;
  public static final int BASE_VIT_PER_LEVEL = 1;

  // Wave Scaling (THE GOLDEN RATIO)
  public static final double ENEMY_HP_MULTIPLIER = 1.08; 
  public static final double ENEMY_STRENGTH_MULTIPLIER = 1.05; 
  public static final double ENEMY_EXP_MULTIPLIER = 1.07; 

  // Enemy Skill Cooldowns
  public static final int NORMAL_ENEMY_ATTACK_COOLDOWN = 0;
  public static final int ELITE_ENEMY_ATTACK_COOLDOWN = 1;
  public static final int BOSS_ENEMY_ATTACK_COOLDOWN = 2;

  // Enemy Templates
  public static final List<Enemy> NORMAL_ENEMIES = new ArrayList<Enemy>();
  public static final List<Enemy> ELITE_ENEMIES = new ArrayList<Enemy>();
  public static final List<Enemy> BOSS_ENEMIES = new ArrayList<Enemy>();

  static {
    NORMAL_ENEMIES.add(new Enemy("Goblin", 60, 8, 40, EnemyType.NORMAL)); 
    NORMAL_ENEMIES.add(new Enemy("Orc", 100, 14, 60, EnemyType.NORMAL));

    ELITE_ENEMIES.add(new Enemy("Troll", 250, 25, 150, EnemyType.ELITE));
    ELITE_ENEMIES.add(new Enemy("Dark Knight", 300, 30, 180, EnemyType.ELITE));

    BOSS_ENEMIES.add(new Enemy("Dragon", 600, 45, 400, EnemyType.BOSS));
    BOSS_ENEMIES.add(new Enemy("Demon Lord", 800, 55, 500, EnemyType.BOSS));
  }
}