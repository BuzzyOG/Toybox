package com.mctoybox.plugin.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.plugin.MainClass;
import com.mctoybox.plugin.classes.ClassTypes;
import com.mctoybox.plugin.classes.ClassBase;

public class PlayerInteract implements Listener {
	private MainClass mainClass;

	public PlayerInteract(MainClass mainClass) {
		this.mainClass = mainClass;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!event.hasBlock())
			return;

		SpoutPlayer p = (SpoutPlayer) event.getPlayer();

		Block activated = event.getClickedBlock();
		if (activated.getType().equals(Material.WOOD_BUTTON) || activated.getType().equals(Material.STONE_BUTTON) || activated.getType().equals(Material.SIGN_POST) || activated.getType().equals(Material.WALL_SIGN)) {
			for (ClassBase c : mainClass.ClassList) {
				String s = c.getClassRef().getName();
				if ((mainClass.getConfig().getInt("classSigns." + s + ".x") == activated.getX()) && (mainClass.getConfig().getInt("classSigns." + s + ".y") == activated.getY()) && (mainClass.getConfig().getInt("classSigns." + s + ".z") == activated.getZ())) {
					//Checking if the clicked sign/button is a classsign
					if(mainClass.playerClasses.get(p.getName()).equals(ClassTypes.OUTSIDER))
					{
						//Player is NOT already a class
					}
				}
			}
		}
	}
}
