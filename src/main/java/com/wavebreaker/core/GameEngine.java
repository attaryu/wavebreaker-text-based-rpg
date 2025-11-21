package com.wavebreaker.core;

import com.wavebreaker.managers.CombatManager;
import com.wavebreaker.managers.WaveManager;
import com.wavebreaker.models.Enemy;
import com.wavebreaker.models.Player;
import com.wavebreaker.utils.Delay;
import com.wavebreaker.utils.Input;

public class GameEngine {
  private static final WaveManager waveManager = new WaveManager();
  private static final CombatManager combatManager = new CombatManager();

  public static void start() {
    System.out.println("╔════════════════════════╗");
    System.out.println("║   WAVE BREAKER - RPG   ║");
    System.out.println("╚════════════════════════╝");

    String playerName = Input.get("\nNama Hero: ");

    Player player = new Player(playerName);

    do {
      if (player.isPlayerHasUnusedStatPoints()) {
        player.showStat();
        String menu = Input.get("Alokasi sekarang? (y/n): ");

        if (menu.equalsIgnoreCase("y")) {
          player.allocateStatPoint();
        }
      }

      Input.get("\n[Enter] Lanjut wave berikutnya...");

      Delay.clearConsole();

      Enemy enemy = waveManager.nextWave();

      combatManager.startCombat(player, enemy);

      while (!combatManager.isCombatOver()) {
        combatManager.processPlayerTurn();
      }

      Delay.wait(500);

      if (player.isAlive()) {
        System.out.println("\nWave " + waveManager.getCurrentWave() + " selesai! +" + enemy.getExpReward() + " EXP");

        if (waveManager.isWavesEnded()) {
          System.out.println("\n╔════════════════════════╗");
          System.out.println("║     ANDA MENANG!       ║");
          System.out.println("╚════════════════════════╝");
          System.out.println("Selamat " + player.getName() + "!");

          break;
        }

        player.gainExp(enemy.getExpReward());
        Delay.wait(400);
      } else {
        System.out.println("\n╔═══════════════════╗");
        System.out.println("║     GAME OVER     ║");
        System.out.println("╚═══════════════════╝");
        break;
      }
    } while (!waveManager.isWavesEnded());
  }
}
