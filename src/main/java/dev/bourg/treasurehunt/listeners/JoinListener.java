package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private GameManager gameManager;

    public JoinListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        gameManager.getPlayerManager().changeSkin(event.getPlayer());
    }

}
