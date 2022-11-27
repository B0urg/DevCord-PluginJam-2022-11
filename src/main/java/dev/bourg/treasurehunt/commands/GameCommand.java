package dev.bourg.treasurehunt.commands;

import dev.bourg.treasurehunt.game.GameManager;
import dev.bourg.treasurehunt.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GameCommand implements CommandExecutor {

    private GameManager gameManager;

    public GameCommand(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("no");
            return false;
        }
        Player player = (Player) sender;
        if(player.getGameMode() != GameMode.SURVIVAL) return false;
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("start")){
                this.gameManager.setGameState(GameState.STARTING);
            }else if(args[0].equalsIgnoreCase("cancel")){
                this.gameManager.setGameState(GameState.LOBBY);
            }
        }
        return false;
    }
}
