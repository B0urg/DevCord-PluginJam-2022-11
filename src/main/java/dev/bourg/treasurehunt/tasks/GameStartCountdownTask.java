package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {

    private final GameManager gameManager;

    public int timeLeft;


    public GameStartCountdownTask(GameManager gameManager, Integer time){
        this.gameManager = gameManager;
        this.timeLeft = time;
    }


    @Override
    public void run() {
        timeLeft--;
        if(timeLeft <= 0){
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }
        if(timeLeft % 5 == 0 || timeLeft <= 4){
            if(timeLeft <= 4){
                Bukkit.broadcast(Component.text(("§4Spiel startet in " + timeLeft + " Sekunden!")));
            }else {
                Bukkit.broadcast(Component.text(("§aSpiel startet in " + timeLeft + " Sekunden!")));
            }
            Bukkit.getOnlinePlayers().forEach(player -> {
                    player.showTitle(Title.title(Component.text(timeLeft % 5 == 0 ? "§a" + timeLeft : "§4" + timeLeft), Component.text("")));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
            });
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setLevel(timeLeft / 18);
            player.setExp(timeLeft / 15F);
        });
    }
}
