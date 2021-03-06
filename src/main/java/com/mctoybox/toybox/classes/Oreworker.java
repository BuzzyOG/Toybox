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
