package com.mctoybox.toybox.blocks;

import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.util.Recipes;

public class Stove extends GenericCubeCustomBlock {
	private MainClass mainClass;
	
	public Stove(MainClass mainClass, Plugin plugin, String name, String texture, int textureSize) {
		super(plugin, name, texture, textureSize);
		this.mainClass = mainClass;
		
		Recipes.NewShapedRecipe(this, new String[] {"000", "010", "000"}, new Material[] { MaterialData.cobblestone, MaterialData.glass } );
		Recipes.NewShapedRecipe(this, new String[] {"000", "010", "000"}, new Material[] { MaterialData.cobblestone, MaterialData.glassPane } );
	}
	
	public void onBlockDestroyed(World world, int x, int y, int z) {
		mainClass.debugOutput("Stove destroyed!");
    }
 
    public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
    	
        return true;
    }
}
