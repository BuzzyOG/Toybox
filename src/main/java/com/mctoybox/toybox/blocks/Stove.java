package com.mctoybox.toybox.blocks;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.material.block.GenericCuboidCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;

public class Stove extends GenericCuboidCustomBlock {
	private MainClass mainClass;
	
	public Stove(Plugin plugin) {
		this(plugin, "Stove");
	}
	
	public Stove(Plugin plugin, String name) {
		super(plugin, name);
		this.mainClass = (MainClass) plugin;
		this.setBlockDesign(new GenericCubeBlockDesign(plugin, mainClass.getSettings().getResourceLocation() + "Blocks/Stove.png", 32));
	}
	
	@Override
	public void onBlockDestroyed(World world, int x, int y, int z) {
		mainClass.debugOutput("Stove destroyed!");
	}
	
	public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
		
		return true;
	}
}
