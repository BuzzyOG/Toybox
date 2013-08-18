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

public class Lumberjack extends ClassBase {
	
	private List<Object[]> thisOnlyBreak = new ArrayList<Object[]>();
	private List<Object[]> thisOnlyPlace = new ArrayList<Object[]>();
	
	private List<Material> allowedTools = new ArrayList<Material>();
	
	public Lumberjack(MainClass mainClass) {
		super(mainClass, "Lumberjack", Permissions.CLASSES_LUMBERJACK);
		
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 1 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 2 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 3 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 5 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 6 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 7 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 9 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 10 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 11 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 13 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 14 });
		this.thisOnlyBreak.add(new Object[] { Material.LOG, 15 });
		
		this.thisOnlyBreak.add(new Object[] { Material.LEAVES, 1 });
		this.thisOnlyBreak.add(new Object[] { Material.LEAVES, 2 });
		this.thisOnlyBreak.add(new Object[] { Material.LEAVES, 3 });
		
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 1 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 2 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 3 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 5 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 6 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 7 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 9 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 10 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 11 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 13 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 14 });
		this.thisOnlyPlace.add(new Object[] { Material.LOG, 15 });
		
		this.thisOnlyPlace.add(new Object[] { Material.LEAVES, 1 });
		this.thisOnlyPlace.add(new Object[] { Material.LEAVES, 2 });
		this.thisOnlyPlace.add(new Object[] { Material.LEAVES, 3 });
		
		this.allowedTools.add(Material.STONE_AXE);
		this.allowedTools.add(Material.GOLD_AXE);
		this.allowedTools.add(Material.IRON_AXE);
		this.allowedTools.add(Material.DIAMOND_AXE);
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
		
		if ((block.getType().equals(Material.LOG) && block.getData() != 0 && block.getData() != 4 && block.getData() != 8) || (block.getType().equals(Material.LEAVES) && block.getData() != 0)) {
			if (mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
				event.setCancelled(true);
				player.sendMessage("Only a lumberjack can break that!");
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		SpoutBlock block = (SpoutBlock) event.getBlock();
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		
		if ((block.getType().equals(Material.LOG) && block.getData() != 0 && block.getData() != 4 && block.getData() != 8) || (block.getType().equals(Material.LEAVES) && block.getData() != 0)) {
			if (mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
				event.setCancelled(true);
				player.sendMessage("Only a lumberjack can place that!");
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		// TODO
	}
}
