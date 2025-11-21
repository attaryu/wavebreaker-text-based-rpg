# WaveBreaker - Text-Based RPG

WaveBreaker is a Java-based console RPG where players battle through increasingly difficult waves of enemies. Customize your hero, manage your stats, and defeat powerful bosses to survive all 30 waves!

## 🎮 Features

- **Wave System**: Battle through 30 waves of enemies, including Elites and Bosses.
- **Turn-Based Combat**: Strategic combat system with Attack and Heal skills.
- **RPG Progression**:
  - Level up system with EXP.
  - Allocatable stat points (**STR** for damage, **VIT** for health).
  - Scaling difficulty.
- **Diverse Enemies**: Fight Goblins, Orcs, Trolls, Dark Knights, Dragons, and Demon Lords.
- **Configurable**: All game balance settings are centralized in `Config.java`.

## 🕹️ How to Play

1. **Start the Game**: Run `Main.java`.
2. **Create Character**: Enter your hero's name.
3. **Combat**:
   - Choose `[1]` to Attack.
   - Choose `[2]` to Heal (Cooldown applies).
4. **Progression**:
   - Defeat enemies to gain EXP.
   - Level up to get Stat Points.
   - Allocate points to **STR** (Strength) or **VIT** (Vitality) to build your character.
5. **Goal**: Survive until Wave 30 and defeat the final boss!

## 🛠️ Project Structure

- `src/main/java/com/wavebreaker/`
  - `core/`: Main game loop and engine.
  - `managers/`: Handles combat, waves, and leveling logic.
  - `models/`: Player, Enemy, and Character classes.
  - `config/`: Centralized game configuration.
  - `utils/`: Helper classes for Input and Delays.

## ⚙️ Configuration

Game balance can be easily tuned in `src/main/java/com/wavebreaker/config/Config.java`. You can adjust:
- Player base stats and scaling.
- Enemy stats and templates.
- Wave difficulty multipliers.
- Leveling curve.

## 🚀 Requirements

- Java JDK 8 or higher.
