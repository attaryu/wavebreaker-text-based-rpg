package com.wavebreaker.config;

import java.util.ArrayList;
import java.util.List;

import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.EnemyType;

public class Config {
  // Game Settings
  public static final int INITIAL_WAVE = 1;
  public static final int MAX_WAVES = 30;

  // Player Base Stats (STARTING STRONG)
  // Kita set HP tinggi agar kamu tidak mati konyol di Wave 2.
  public static final int PLAYER_BASE_HP = 200;     
  public static final int PLAYER_BASE_STRENGTH = 20; 
  
  // Cooldown Management
  public static final int PLAYER_ATTACK_COOLDOWN = 0;
  public static final int PLAYER_HEAL_COOLDOWN = 4; // Naikkan ke 4 karena HP reset tiap wave.
  public static final double PLAYER_HEAL_PERCENTAGE = 0.30; // 30% dari 200 = 60 HP. Cukup.

  // Player Stat Scaling (HIGH REWARD)
  // Karena musuh scaling eksponensial, player juga harus scaling kuat.
  public static final int PLAYER_HP_PER_VIT = 15;   // 1 point VIT = +15 HP
  public static final int PLAYER_STRENGTH_PER_STR = 3; // 1 point STR = +3 DMG

  // Leveling System
  public static final int BASE_EXP_REQUIREMENT = 100;
  public static final double EXP_GROWTH_RATE = 1.25; 
  public static final int ALLOCATED_STAT_POINTS_PER_LEVEL = 3;
  
  // Auto-scaling level up (Bonus kecil)
  public static final int BASE_STR_PER_LEVEL = 1;
  public static final int BASE_VIT_PER_LEVEL = 1;

  // Wave Scaling (THE GOLDEN RATIO)
  // KUNCI: Jangan gunakan angka di atas 1.08 untuk eksponensial 30 wave!
  
  // HP Musuh naik 8% per wave. Wave 30 = ~9x HP awal.
  public static final double ENEMY_HP_MULTIPLIER = 1.08; 
  
  // Strength musuh naik 5% per wave. Wave 30 = ~4x Damage awal.
  // Kita tahan damage musuh agar tidak one-shot player.
  public static final double ENEMY_STRENGTH_MULTIPLIER = 1.05; 
  
  // XP naik 7% per wave agar leveling player terjaga.
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
    // --- WAVE 1-10 (EARLY) ---
    // Goblin: HP Kertas, Damage Geli (Free XP)
    NORMAL_ENEMIES.add(new Enemy("Goblin", 60, 8, 40, EnemyType.NORMAL)); 
    
    // Orc: Target Latihan. 
    // HP 100 vs Player DMG 20 -> 5 hits kill.
    // DMG 14 vs Player HP 200 -> 14 hits kill player. 
    // Player Menang Mudah.
    NORMAL_ENEMIES.add(new Enemy("Orc", 100, 14, 60, EnemyType.NORMAL));

    // --- WAVE 10-20 (MID) ---
    // Troll (Elite): Mulai keras.
    ELITE_ENEMIES.add(new Enemy("Troll", 250, 25, 150, EnemyType.ELITE));
    
    // Dark Knight: Damage Dealer.
    ELITE_ENEMIES.add(new Enemy("Dark Knight", 300, 30, 180, EnemyType.ELITE));

    // --- WAVE 20-30 (END) ---
    // Dragon: Boss HP Tebal
    BOSS_ENEMIES.add(new Enemy("Dragon", 600, 45, 400, EnemyType.BOSS));
    
    // Demon Lord: Boss Terakhir
    // Base 800 HP, 55 DMG.
    // Di Wave 30 (Multiplier ~1.05^29 = x4.1) -> DMG jadi ~225 per hit!
    // Player WAJIB punya HP 1000+ di sini (via VIT).
    BOSS_ENEMIES.add(new Enemy("Demon Lord", 800, 55, 500, EnemyType.BOSS));
  }
}