package dev.bourg.treasurehunt.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class VoteManager {

    private boolean running = false;
    private HashMap<Player, Boolean> votes = new HashMap<>();

    private GameManager gameManager;

    public VoteManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void addVote(boolean bool, Player player){
        int players = Bukkit.getOnlinePlayers().stream().filter(player1 -> player1.getGameMode() == GameMode.SURVIVAL).toArray().length;
        if(!running) return;
        if(votes.size() == players) return;
        votes.put(player, bool);
    }
    public Boolean getResult(){
        int players = Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).toArray().length;
        AtomicReference<Integer> yes = new AtomicReference<>(0);
        votes.forEach((player, aBoolean) -> {
            if(aBoolean){
                yes.getAndSet(yes.get() + 1);
            }
        });
        return new Random().nextInt(players) < yes.get();
    }
    public void stopVoting(){
        votes.clear();
        running = false;
    }
    public void startVoting(){
        this.running = true;
    }





}
