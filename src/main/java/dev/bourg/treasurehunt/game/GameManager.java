package dev.bourg.treasurehunt.game;

import dev.bourg.treasurehunt.TreasureHunt;
import dev.bourg.treasurehunt.tasks.FinalTimerTask;
import dev.bourg.treasurehunt.tasks.GameStartCountdownTask;
import dev.bourg.treasurehunt.tasks.WaitForCompleteSecondQuestTask;
import dev.bourg.treasurehunt.tasks.WaitForFirstQuestTask;
import dev.bourg.treasurehunt.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.objectweb.asm.commons.ModuleTargetAttribute;

import java.util.ArrayList;
import java.util.UUID;

public class GameManager {

    private final TreasureHunt plugin;
    private GameState gameState = GameState.LOBBY;
    private final BlockManager blockManager;
    private final PlayerManager playerManager;
    private final VoteManager voteManager;
    private GameStartCountdownTask gameStartCountdownTask;
    private WaitForFirstQuestTask waitForFirstQuestTask;
    private ArmorStand moutainViewer;
    private ArmorStand intersceneArmorStand;
    private WaitForCompleteSecondQuestTask waitForCompleteSecondQuestTask;
    private int completedquests;
    private boolean won;


    public GameManager(TreasureHunt plugin){
        this.voteManager = new VoteManager(this);
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
                this.resetMap();
                if(this.gameStartCountdownTask != null) this.gameStartCountdownTask.cancel();
                Bukkit.broadcast(Component.text("§aGame started"));
                Inventory inventory = Bukkit.createInventory(null, 1*9 , Component.text("Side Quest 1?[+2 Gold]"));
                inventory.setItem(2, new ItemBuilder(Material.LIME_DYE).setDisplayname("Ja").build());
                inventory.setItem(6, new ItemBuilder(Material.RED_DYE).setDisplayname("Nein").build());
                this.voteManager.startVoting();
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.openInventory(inventory);
                });
                this.waitForFirstQuestTask = new WaitForFirstQuestTask(this);
                this.waitForFirstQuestTask.runTaskLater(plugin, 300);



            }
            case STARTING -> {
                this.gameStartCountdownTask = new GameStartCountdownTask(this, 31);
                this.gameStartCountdownTask.runTaskTimer(plugin, 0, 20);
            }
            case SEARCH -> {
                ArrayList<UUID> spectating = new ArrayList<>();
                Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                    player.setGameMode(GameMode.SPECTATOR);
                    spectating.add(player.getUniqueId());
                    player.teleport(moutainViewer.getLocation());
                    player.setSpectatorTarget(moutainViewer);
                });
                if(completedquests >= 1){
                    Bukkit.broadcast(Component.text("§6§l +2 Gold"));
                    Bukkit.broadcast(Component.text("§aJetzt da du deine erste Side Quest erledigt hast und dein Gold erhalten hast, musst du denn Bunker mit den verborgenen Schätzen finden und ihn öffnen(in die mitte der Tür klicken). §e(Tipp: Der Eingang befindet sich wie im Film im Boden und ihr werdet im umkreis von 30 Blöcken von der X Kordinate ausgesetzt.)"));
                }else {
                    Bukkit.broadcast(Component.text("§aNun musst du denn Bunker mit den verborgenen Schätzen finden und ihn öffnen(in die mitte der Tür klicken). §e(Tipp: Der Eingang befindet sich wie im Film im Boden und ihr werdet im umkreis von 30 Blöcken von der X Kordinate ausgesetzt.)"));
                }
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (UUID uuid : spectating) {
                            Player player = Bukkit.getPlayer(uuid);
                            player.setGameMode(GameMode.SURVIVAL);
                            player.teleport(new Location(Bukkit.getWorld("world"), 177, 79, 43));
                        }
                    }
                }, 200);
            }
            case QUEST1 -> {
                this.waitForFirstQuestTask.cancel();
            }
            case QUEST2 -> {

                this.waitForCompleteSecondQuestTask = new WaitForCompleteSecondQuestTask(this);
                waitForCompleteSecondQuestTask.runTaskTimer(plugin, 0, 20);
            }

            case DISCOVERING -> {
                Bukkit.broadcast(Component.text("§aIhr habt das alte Lager entdeckt"));
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcast(Component.text("§aDoch was ist das? Auf einmal taucht interpol auf, uhr müsst flüchten und steigt in ein altes auto!"));
                    }
                }, 200);
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        setGameState(GameState.FINAL);
                    }
                }, 400);
            }
            case FINAL -> {
                Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                    if(completedquests == 0){
                        player.teleport(new Location(Bukkit.getWorld("world"), 169, 54, 37));
                    }else if(completedquests == 1) {
                        player.teleport(new Location(Bukkit.getWorld("world"), 169, 54, 6));
                    }else {
                        player.teleport(new Location(Bukkit.getWorld("world"), 169, 54, -7));
                    }
                });
                if(completedquests == 1){
                    Bukkit.broadcast(Component.text("§aDoch was ist passiert, euer Gold ist zu schwer und das auto ist liegen geblieben. Hättet ihr doch nur kein Gold genommen"));

                }else if(completedquests == 2) {
                    Bukkit.broadcast(Component.text("§aDoch was ist passiert, euer Gold ist zu schwer und das auto ist liegen geblieben. Hättet ihr doch nur kein/weniger Gold genommen"));
                }
                FinalTimerTask timerTask = new FinalTimerTask(this);
                timerTask.runTaskTimer(plugin, 0, 20);
            }
            case WON -> {
                Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setSpectatorTarget(moutainViewer);
                });
                if(won){
                    Bukkit.broadcast(Component.text("§aGlückwunsch ihr habt gewonnen entweder seid ihr gut in jump and run oder seid dem motto gefolgt"));
                }else {
                    Bukkit.broadcast(Component.text("§4Leider habt ihr es nicht geschaft. Vieleicht nächstes mal"));
                }
                setGameState(GameState.LOBBY);
            }
            case LOBBY -> {
                Bukkit.getOnlinePlayers().forEach(player ->  {
                    player.teleport(new Location(Bukkit.getWorld("world"), 101, 96, 135, -180, 1));
                    player.setGameMode(GameMode.SURVIVAL);

                });
            }

        }
    }
    public void resetMap(){
        World world = Bukkit.getWorld("world");
        world.getEntitiesByClass(ArmorStand.class).forEach(a -> a.setHealth(0));
        moutainViewer = world.spawn(new Location(world, 101, 96, 135, -180, 1), ArmorStand.class);
        moutainViewer.setInvisible(true);
        moutainViewer.setInvulnerable(true);
        intersceneArmorStand = world.spawn(new Location(world, 186, 55, -6), ArmorStand.class);
        intersceneArmorStand.setInvulnerable(true);
        intersceneArmorStand.setInvisible(true);

        // Closing the Bunker door
        world.getBlockAt(202, 72, 27).setType(Material.IRON_BLOCK);
        Location[] outerdoor = {new Location(world, 202, 72, 26), new Location(world, 203, 72, 26), new Location(world, 201, 72, 26),  new Location(world, 201, 72, 27), new Location(world, 201, 72, 28), new Location(world, 202, 72, 28), new Location(world, 203, 72, 28), new Location(world, 203, 72, 27)};
        for (Location location : outerdoor){
            Block block = world.getBlockAt(location);
            block.setType(Material.IRON_TRAPDOOR);
            TrapDoor trapDoor = (TrapDoor) block.getBlockData();
            trapDoor.setHalf(Bisected.Half.TOP);
            trapDoor.setFacing(BlockFace.NORTH);

        }

        world.getBlockAt(200, 60, 21).setType(Material.POLISHED_ANDESITE);
        world.getBlockAt( 200, 62, 21).setType(Material.POLISHED_ANDESITE);
        world.getBlockAt( 198, 62, 21).setType(Material.POLISHED_ANDESITE);
        world.getBlockAt( 198, 60, 21).setType(Material.POLISHED_ANDESITE);
        world.getBlockAt(199 ,60, 21).setType(Material.ANDESITE);
        world.getBlockAt(200, 61, 21).setType(Material.ANDESITE);
        world.getBlockAt(199, 62, 21).setType(Material.ANDESITE);
        world.getBlockAt(198, 61, 21).setType(Material.ANDESITE);
        world.getBlockAt(199, 61, 21 ).setType(Material.LODESTONE);

        world.getBlockAt(186 ,54, 46).setType(Material.REDSTONE_BLOCK);
        if(world.getBlockAt(188 ,54, 46).getType() == Material.REDSTONE_BLOCK) world.getBlockAt(188, 54, 46).setType(Material.AIR);


        // Placing the concrete
        for(int x = 201; x < 204;x++){
            for (int z = 26; z < 29; z++){
                world.getBlockAt(x, 71, z).setType(Material.BLACK_CONCRETE);
            }
        }
        Bukkit.getWorld("world").getBlockAt(205, 72, 25).setType(Material.GRASS_BLOCK);
        completedquests = 0;
    }
    public BlockManager getBlockManager(){
        return blockManager;
    }
    public PlayerManager getPlayerManager(){
        return playerManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public TreasureHunt getPlugin() {
        return plugin;
    }

    public GameState getGameState(){
        return this.gameState;
    }

    public int getCompletedquests() {
        return completedquests;
    }

    public void setCompletedquests(int completedquests) {
        this.completedquests = completedquests;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isRunning(){
        return gameState != GameState.LOBBY;
    }

}
