package com.mctoybox.toybox.listeners;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;

@SuppressWarnings("unused")
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
		
	}
}
