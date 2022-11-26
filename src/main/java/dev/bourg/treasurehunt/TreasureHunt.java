package dev.bourg.treasurehunt;

import dev.bourg.treasurehunt.commands.GameCommand;
import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.listeners.BlockBreakListener;
import dev.bourg.treasurehunt.listeners.JoinListener;
import dev.bourg.treasurehunt.tabCompleters.GameCommandCompleter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreasureHunt extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.gameManager = new GameManager(this);
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        getCommand("game").setExecutor(new GameCommand(gameManager));
        getCommand("game").setTabCompleter(new GameCommandCompleter());
    }
    public void registerEvents(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(this.gameManager), this);
        pluginManager.registerEvents(new JoinListener(this.gameManager), this);
    }

}
