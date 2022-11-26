package dev.bourg.treasurehunt.game;

import dev.bourg.treasurehunt.TreasureHunt;
import dev.bourg.treasurehunt.tasks.GameStartCountdownTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class GameManager {

    private final TreasureHunt plugin;
    private GameState gameState = GameState.LOBBY;
    private final BlockManager blockManager;
    private final PlayerManager playerManager;
    private GameStartCountdownTask gameStartCountdownTask;

    public GameManager(TreasureHunt plugin){
        this.blockManager = new BlockManager(this);
        this.playerManager = new PlayerManager(this);
        this.plugin = plugin;

    }

    public void setGameState(GameState gameState){
        if(this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) return;
        if(this.gameState == gameState) return;
        this.gameState = gameState;
        switch (gameState){
            case ACTIVE ->  {
                if(this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                Bukkit.broadcast(Component.text("Active"));
            }
            case STARTING -> {


                this.gameStartCountdownTask = new GameStartCountdownTask(this, 31);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0, 20);
            }
        }
    }
    public BlockManager getBlockManager(){
        return blockManager;
    }
    public PlayerManager getPlayerManager(){
        return playerManager;
    }
    public boolean isRunning(){
        return gameState == GameState.ACTIVE || gameState == GameState.FINAL;
    }

}
