package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import dev.bourg.treasurehunt.tasks.WaitForSecondQuestTask;
import dev.bourg.treasurehunt.utils.ItemBuilder;
import io.papermc.paper.configuration.type.fallback.FallbackValue;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.net.http.WebSocket;

public class InteractListener implements Listener {

    private final GameManager gameManager;

    public InteractListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getClickedBlock().getType().equals(Material.POLISHED_BLACKSTONE_BUTTON)){
              if(event.getClickedBlock().getLocation().getX() == -75 && event.getClickedBlock().getLocation().getY() == 83 && event.getClickedBlock().getLocation().getZ() == 73){
                  if(gameManager.getGameState() == GameState.QUEST1){
                      Bukkit.broadcast(Component.text("§a§l" + event.getPlayer().getName() + "§r§a hat den Knopf gefunden!"));
                      gameManager.setGameState(GameState.SEARCH);
                      this.gameManager.setCompletedquests(gameManager.getCompletedquests() + 1);
                  }
              }
            }
            if(event.getClickedBlock().getType().equals(Material.IRON_BLOCK)){
                if(event.getClickedBlock().getLocation().getX() == 202 && event.getClickedBlock().getLocation().getY() == 72 && event.getClickedBlock().getLocation().getZ() == 27){
                    Bukkit.broadcast(Component.text("§a§l" + event.getPlayer().getName() + "§r§a hat den eingang gefunden"));
                    for(int x = 201; x < 204;x++){
                        for (int y = 71; y < 73; y++){
                            for (int z = 26; z < 29; z++){
                                Bukkit.getWorld("world").getBlockAt(x, y, z).setType(Material.AIR);
                            }
                        }
                    }
                    Bukkit.getWorld("world").getBlockAt(205, 72, 25).setType(Material.GOLD_BLOCK);
                    Block stairs = Bukkit.getWorld("world").getBlockAt(203 ,71, 27);
                    stairs.setType(Material.STONE_BRICK_STAIRS);
                    ((Stairs)stairs.getBlockData()).setFacing(BlockFace.NORTH);
                    Block slab = Bukkit.getWorld("world").getBlockAt(203, 72, 26);
                    slab.setType(Material.STONE_BRICK_SLAB);
                    ((Slab)slab.getBlockData()).setType(Slab.Type.BOTTOM);
                    Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                        player.teleport(new Location(Bukkit.getWorld("world"), 205, 73, 25));
                    });

                    Inventory inventory = Bukkit.createInventory(null, 1*9 , Component.text("Side Quest 2? [+2Gold]"));
                    inventory.setItem(2, new ItemBuilder(Material.GREEN_DYE).setDisplayname("Ja").build());
                    inventory.setItem(6, new ItemBuilder(Material.RED_DYE).setDisplayname("Nein").build());
                    this.gameManager.getVoteManager().startVoting();
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.openInventory(inventory);
                    });
                    WaitForSecondQuestTask waitForSecondQuestTask = new WaitForSecondQuestTask(gameManager);
                    waitForSecondQuestTask.runTaskLater(gameManager.getPlugin(), 250);

                }
            }else if(event.getClickedBlock().getType().equals(Material.LODESTONE)){
                if(gameManager.getGameState() != GameState.DISCOVERING) return;
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "fill 198 62 21 200 60 21 air");
            }


        }
    }

}
