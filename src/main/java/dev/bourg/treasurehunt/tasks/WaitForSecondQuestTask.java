package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitForSecondQuestTask extends BukkitRunnable {


    private final GameManager gameManager;


    public WaitForSecondQuestTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public void run() {
        if(this.gameManager.getVoteManager().getResult()){
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                player.teleport(new Location(Bukkit.getWorld("world"), 187, 56, 43));
            });
            Bukkit.broadcast(Component.text("Die zweite Quest wird ein kleines redstone Rätsel, Ziel ist es das redstone unter dem roten glass zu deaktivieren"));
            gameManager.setGameState(GameState.QUEST2);
        }else {
            gameManager.setGameState(GameState.DISCOVERING);
        }

    }

}
