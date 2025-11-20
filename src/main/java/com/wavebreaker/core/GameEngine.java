package com.wavebreaker.core;

import com.wavebreaker.managers.CombatManager;
import com.wavebreaker.managers.WaveManager;
import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.Player;
import com.wavebreaker.utils.Input;

public class GameEngine {
  private static final WaveManager waveManager = new WaveManager();
  private static final CombatManager combatManager = new CombatManager();

  public static void start() {
    System.out.println("Selamat datang di Wave Breaker!");

    String playerName = Input.get("Masukkan nama pemain: ");

    Player player = new Player(playerName);

    do {
      if (player.isPlayerHasUnusedStatPoints()) {
        System.out.println("Terdapat poin statistik yang belum digunakan!");
        String menu = Input.get("Apakah Anda ingin mengalokasikannya sekarang? (y/n): ");

        if (menu.equalsIgnoreCase("y")) {
          player.allocateStatPoint();
        }
      }

      Input.get("Tekan Enter untuk siap ke gelombang berikutnya...");

      Enemy enemy = waveManager.nextWave();

      player.resetAllStates();
      combatManager.startCombat(player, enemy);

      while (!combatManager.isCombatOver()) {
        combatManager.processPlayerTurn();
      }

      if (player.isAlive()) {
        System.out.println("Selamat! Gelombang musuh ke-" + waveManager.getCurrentWave() + " telah selesai.");

        if (waveManager.isWavesEnded()) {
          System.out.println("Wow! Semua gelombang musuh telah musnah!");
          System.out.println("Selamat " + player.getName() + ", kamu telah memenangkan permainan!");

          break;
        }

        player.gainExp(enemy.getExpReward());
      } else {
        System.out.println("Game Over!");
        break;
      }
    } while (!waveManager.isWavesEnded());
  }
}
