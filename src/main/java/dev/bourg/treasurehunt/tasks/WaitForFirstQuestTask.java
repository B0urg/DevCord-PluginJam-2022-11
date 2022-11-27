package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitForFirstQuestTask extends BukkitRunnable {

    private final GameManager gameManager;

    public WaitForFirstQuestTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public void run() {
        if(this.gameManager.getVoteManager().getResult()){
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                player.teleport(new Location(Bukkit.getWorld("world"), -46, 75, 104));
            });
            Bukkit.broadcast(Component.text("Die erste Quest wird eine klassische runde find the button sein."));
            gameManager.setGameState(GameState.QUEST1);
        }else {
            gameManager.setGameState(GameState.SEARCH);
        }

    }
}
