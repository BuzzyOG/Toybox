package com.mctoybox.plugin.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.plugin.MainClass;
import com.mctoybox.plugin.Permissions;

public class Oreworker extends ClassBase {
	
	private List<Material> thisOnlyBreak = new ArrayList<Material>();
	private List<Material> thisOnlyPlace = new ArrayList<Material>();
	
	private List<Material> allowedTools = new ArrayList<Material>();
	
	public Oreworker(MainClass mainClass) {
		super(mainClass, "Oreworker", Permissions.CLASSES_OREWORKER);
		
		this.thisOnlyBreak.add(Material.IRON_ORE);
		this.thisOnlyBreak.add(Material.GOLD_ORE);
		this.thisOnlyBreak.add(Material.REDSTONE_ORE);
		this.thisOnlyBreak.add(Material.DIAMOND_ORE);
		this.thisOnlyBreak.add(Material.QUARTZ_ORE);
		this.thisOnlyBreak.add(Material.LAPIS_ORE);
		
		this.thisOnlyPlace.add(Material.IRON_ORE);
		this.thisOnlyPlace.add(Material.GOLD_ORE);
		this.thisOnlyPlace.add(Material.REDSTONE_ORE);
		this.thisOnlyPlace.add(Material.DIAMOND_ORE);
		this.thisOnlyPlace.add(Material.QUARTZ_ORE);
		this.thisOnlyPlace.add(Material.LAPIS_ORE);
		
		this.allowedTools.add(Material.STONE_PICKAXE);
		this.allowedTools.add(Material.IRON_PICKAXE);
		this.allowedTools.add(Material.GOLD_PICKAXE);
		this.allowedTools.add(Material.DIAMOND_PICKAXE);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		SpoutBlock block = (SpoutBlock) event.getBlock();
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		
		if (!thisOnlyBreak.contains(block.getType()))
			return;
		
		if (!mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
			event.setCancelled(true);
			player.sendMessage("Only an oreworker can break that!");
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		SpoutBlock block = (SpoutBlock) event.getBlock();
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		
		if (!thisOnlyPlace.contains(block.getType()))
			return;
		
		if (mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
			event.setCancelled(true);
			player.sendMessage("Only an oreworker can place that!");
		}
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		// TODO
	}
}
