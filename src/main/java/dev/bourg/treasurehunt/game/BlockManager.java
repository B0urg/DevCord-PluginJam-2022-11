package dev.bourg.treasurehunt.game;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class BlockManager {

    public GameManager gameManager;

    public BlockManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    private final Set<Material> allowedToBreak = new HashSet<>();

    public void addAllowedMaterial(Material material){
        if(allowedToBreak.contains(material)) return;
        allowedToBreak.add(material);
    }
    public void removeMaterial(Material material){
        if(!allowedToBreak.contains(material)) return;
        allowedToBreak.add(material);
    }

    public boolean canBreak(Block block){
        return allowedToBreak.contains(block.getType());
    }

}
