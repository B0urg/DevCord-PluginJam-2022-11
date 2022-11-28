package dev.bourg.treasurehunt.listeners;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import dev.bourg.treasurehunt.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinListener implements Listener {

    private GameManager gameManager;

    public JoinListener(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 101, 96, 135, -180, 1));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999999, 255, true, false));

        if(gameManager.isRunning()){
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            return;
        }
        gameManager.getPlayerManager().changeSkin(event.getPlayer());
        if(!event.getPlayer().getInventory().contains(Material.CLOCK)) event.getPlayer().getInventory().addItem(new ItemBuilder(Material.CLOCK).setDisplayname("Komische ww2 Uhr").build());



    }

}
