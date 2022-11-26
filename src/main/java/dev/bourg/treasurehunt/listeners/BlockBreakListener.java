package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    public GameManager gameManager;

    public BlockBreakListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!gameManager.getBlockManager().canBreak(event.getBlock())){
            event.setCancelled(true);
        }
    }

}
