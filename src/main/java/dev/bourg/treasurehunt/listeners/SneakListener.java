package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.net.http.WebSocket;

public class SneakListener implements Listener {

    private GameManager gameManager;

    public SneakListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(gameManager.getGameState() == GameState.SEARCH || gameManager.getGameState() == GameState.QUEST2) event.setCancelled(true);
    }

}
