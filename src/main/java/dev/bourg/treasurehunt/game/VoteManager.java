package dev.bourg.treasurehunt.game;

import io.netty.channel.epoll.Epoll;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class VoteManager {

    private boolean running = false;
    private Integer players = 0;
    private ArrayList<Boolean> votes = new ArrayList<>();

    private GameManager gameManager;


    public VoteManager(GameManager gameManager){
        this.gameManager = gameManager;
    }
    public void setPlayers(int amount){
        this.players = amount;
    }
    public void addVote(boolean bool){
        if(!running)return;
        if(votes.size() == players) return;
        votes.add(bool);

    }
    public Boolean getResult(){
        if(votes.isEmpty()) return false;
        AtomicInteger i = new AtomicInteger();
        votes.forEach(aBoolean -> {
            if(aBoolean){
                i.getAndIncrement();
            }
        });
        boolean bool = i.get() >= players /2;
        votes = new ArrayList<>();
        running = false;
        return bool;
    }
    public void startVoting(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.getGameMode() == GameMode.SURVIVAL){
                players++;
            }
        }
        this.running = true;
    }





}
