package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.TimerTask;

public class FinalTimerTask extends BukkitRunnable {
    private GameManager gameManager;
    private int timeLeft=15;
    private ArrayList<Boolean> finished = new ArrayList<>();



    public FinalTimerTask(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        timeLeft--;
        if(timeLeft==0){
            cancel();
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                if(player.getLocation().getZ() < 64){
                    finished.add(false);
                }else {
                    finished.add(true);
                }
            });
            gameManager.setWon(!finished.contains(false));
            gameManager.setGameState(GameState.WON);
            return;
        }
        if(timeLeft%5==0){
            Bukkit.broadcast(Component.text("§a Noch " + timeLeft + " Sekunden!"));
        }
    }
}
