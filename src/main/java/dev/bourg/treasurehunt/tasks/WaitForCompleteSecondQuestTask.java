package dev.bourg.treasurehunt.tasks;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.Redstone;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class WaitForCompleteSecondQuestTask extends BukkitRunnable {

    private GameManager gameManager;
    private Player player;
    private ArrayList<UUID> spectating;

    public WaitForCompleteSecondQuestTask(GameManager gameManager, ArrayList<UUID> spectating){
        this.gameManager = gameManager;
        this.player = Bukkit.getPlayer("Hadde");
        this.spectating = spectating;
    }

    @Override
    public void run() {
        Block block = Bukkit.getWorld("world").getBlockAt(187, 53, 43);
        if(!block.isBlockPowered()){
            cancel();
            Bukkit.broadcast(Component.text("§a§l" + player.getName() + "§r§a hat das rätsel gelöst"));
            for (UUID uuid : spectating) {
                Player player1 = Bukkit.getPlayer(uuid);
                player1.setGameMode(GameMode.SURVIVAL);
                player1.teleport( new Location(Bukkit.getWorld("world"), 199, 60, 24));
            }
            player.teleport(new Location(Bukkit.getWorld("world"), 199, 60, 24));
            Bukkit.broadcast(Component.text("§aJetzt da ihr die zweite Quest erledigt habt könnz ihr weiter dazu mit der Uhr in die mitte der Tür klicken."));
            this.gameManager.setGameState(GameState.DISCOVERING);
            this.gameManager.setCompletedquests(gameManager.getCompletedquests() + 1);
            return;
        }
    }
}
