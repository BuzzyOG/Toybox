package com.mctoybox.toybox.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.block.GenericCuboidCustomBlock;

import com.mctoybox.toybox.MainClass;

public class Plaster extends GenericCuboidCustomBlock implements Listener {
	private MainClass mainClass;
	
	public Plaster(Plugin plugin, String colour) {
		super(plugin, colour + " Plaster", 1);
		
		this.mainClass = (MainClass) plugin;
		this.setBlockDesign(new GenericCubeBlockDesign(plugin, mainClass.getSettings().getResourceLocation() + "Blocks/" + "Plaster" + colour.replaceAll(" ", "") + ".png", 32));
		
		if (colour.equalsIgnoreCase("white")) {
			mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
		}
		this.setItemDrop(new SpoutItemStack(this));
		mainClass.debugOutput(colour + " plaster implemented");
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		List<Block> blocksToKeep = new ArrayList<Block>();
		if (!event.getEntityType().equals(EntityType.CREEPER)) {
			return;
		}
		mainClass.debugOutput("test1");
		for (Block b : event.blockList()) {
			SpoutBlock sBlock = (SpoutBlock) b;
			if (sBlock.getCustomBlock().getFullName().toLowerCase().endsWith("plaster")) {
				mainClass.debugOutput("test2");
				blocksToKeep.add(b);
			}
		}
		event.blockList().removeAll(blocksToKeep);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		SpoutBlock block = (SpoutBlock) event.getBlock();
		if (!block.isCustomBlock()) {
			return;
		}
		if (block.getCustomBlock().getFullName().contains("plaster")) {
			event.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		SpoutBlock block = (SpoutBlock) event.getBlock();
		if (!block.isCustomBlock()) {
			return;
		}
		if (block.getCustomBlock().getFullName().contains("plaster")) {
			event.setCancelled(true);
		}
	}
}