package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.net.http.WebSocket;

public class InventoryClickListener implements Listener {
    private GameManager gameManager;

    public InventoryClickListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getCurrentItem() == null)return;
        if(((TextComponent)event.getView().title()).content().equalsIgnoreCase("Side Quest 1?[+2 Gold]") || ((TextComponent)event.getView().title()).content().equalsIgnoreCase("Side Quest 2? [+2Gold]")){
            Player player = (Player) event.getWhoClicked();
            if(!event.getCurrentItem().hasItemMeta())return;
            gameManager.getVoteManager().addVote(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("ja"));
            event.setCancelled(true);
            player.closeInventory();
        }
    }
}
