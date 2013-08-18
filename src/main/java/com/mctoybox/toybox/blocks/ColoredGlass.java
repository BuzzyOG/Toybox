package com.mctoybox.toybox.blocks;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.material.block.GenericCuboidCustomBlock;

import com.mctoybox.toybox.MainClass;

public class ColoredGlass extends GenericCuboidCustomBlock implements Listener {
	private MainClass mainClass;
	
	public ColoredGlass(Plugin plugin, String colour) {
		super(plugin, colour + " Glass", 20);
		
		mainClass = (MainClass) plugin;
		
		this.setBlockDesign(new GenericCubeBlockDesign(plugin, mainClass.getSettings().getResourceLocation() + "Blocks/" + "Glass" + colour.replaceAll(" ", "") + ".png", 32));
		
		this.setOpaque(false);
		// this.setItemDrop(new SpoutItemStack(this));
		this.getBlockDesign().setRenderPass(1);
	}
}
