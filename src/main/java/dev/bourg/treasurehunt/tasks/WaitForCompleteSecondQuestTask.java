package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Redstone;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class WaitForCompleteSecondQuestTask extends BukkitRunnable {

    private GameManager gameManager;

    public WaitForCompleteSecondQuestTask(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        Block block = Bukkit.getWorld("world").getBlockAt(187, 53, 43);
        if(!block.isBlockPowered()){
            cancel();
            Bukkit.getWorld("world").getBlockAt(187, 55, 43).setType(Material.GREEN_STAINED_GLASS);
            Bukkit.broadcast(Component.text("§aDas rätsel wurde gelöst"));
            Bukkit.getScheduler().runTaskLater(gameManager.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.teleport( new Location(Bukkit.getWorld("world"), 199, 60, 24));
                    }
                    Bukkit.broadcast(Component.text("§6§l +2 Gold"));
                    Bukkit.broadcast(Component.text("§aJetzt da ihr die zweite Quest erledigt habt könnz ihr weiter dazu mit der Uhr in die mitte der Tür klicken."));
                    gameManager.setGameState(GameState.DISCOVERING);
                    gameManager.setCompletedquests(gameManager.getCompletedquests() + 1);
                }
            }, 100);
            return;
        }
    }
}
