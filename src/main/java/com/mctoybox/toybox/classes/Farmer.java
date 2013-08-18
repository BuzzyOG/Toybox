package com.mctoybox.toybox.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class Farmer extends ClassBase {
	
	private List<Material> thisOnlyBreak = new ArrayList<Material>();
	private List<Material> thisOnlyPlace = new ArrayList<Material>();
	
	private List<Material> allowedTools = new ArrayList<Material>();
	
	public Farmer(MainClass mainClass) {
		super(mainClass, "Farmer", Permissions.CLASSES_FARMER);
		
		this.thisOnlyBreak.add(Material.POTATO);
		this.thisOnlyBreak.add(Material.SEEDS);
		this.thisOnlyBreak.add(Material.SUGAR_CANE_BLOCK);
		this.thisOnlyBreak.add(Material.WHEAT);
		this.thisOnlyBreak.add(Material.MELON_BLOCK);
		this.thisOnlyBreak.add(Material.MELON_SEEDS);
		this.thisOnlyBreak.add(Material.MELON_STEM);
		this.thisOnlyBreak.add(Material.PUMPKIN);
		this.thisOnlyBreak.add(Material.PUMPKIN_STEM);
		this.thisOnlyBreak.add(Material.PUMPKIN_SEEDS);
		this.thisOnlyBreak.add(Material.COCOA);
		this.thisOnlyBreak.add(Material.CARROT);
		this.thisOnlyBreak.add(Material.POTATO);
		this.thisOnlyBreak.add(Material.CROPS);
		
		this.thisOnlyPlace.add(Material.SEEDS);
		this.thisOnlyPlace.add(Material.POTATO);
		this.thisOnlyPlace.add(Material.CARROT);
		this.thisOnlyPlace.add(Material.PUMPKIN_SEEDS);
		this.thisOnlyPlace.add(Material.MELON_SEEDS);
		this.thisOnlyPlace.add(Material.COCOA);
		this.thisOnlyPlace.add(Material.SUGAR_CANE_BLOCK);
		
		this.allowedTools.add(Material.WOOD_HOE);
		this.allowedTools.add(Material.GOLD_HOE);
		this.allowedTools.add(Material.STONE_HOE);
		this.allowedTools.add(Material.IRON_HOE);
		this.allowedTools.add(Material.DIAMOND_HOE);
	}
	
	@Override
	public void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException {
		if (!player.hasPermission(permRequired.getName())) {
			throw new PlayerNotAllowedClassException();
		}
		
		player.sendMessage(String.format(Message.CLASS_SET_TO.toString(), className));
		
		if (classRef.getType().equals(ClassType.Type.PRIMARY)) {
			mainClass.playerClasses.setPrimaryClass(player.getName(), classRef);
			mainClass.getConfig().set(player.getName() + ".PrimaryClass", classRef.getName());
		}
		else {
			mainClass.playerClasses.setSecondaryClass(player.getName(), classRef);
			mainClass.getConfig().set(player.getName() + ".SecondaryClass", classRef.getName());
		}
		mainClass.playerClasses.updateTitle(player);
		mainClass.saveConfig();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		SpoutBlock block = (SpoutBlock) event.getBlock();
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		
		if (!thisOnlyBreak.contains(block.getType()))
			return;
		
		if (mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
			event.setCancelled(true);
			player.sendMessage("Only farmers can harvest crops!");
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
			player.sendMessage("Only farmers can plant crops!");
		}
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		// TODO
	}
}
