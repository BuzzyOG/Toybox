package com.mctoybox.toybox.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.player.accessories.AccessoryType;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.classes.ClassType;

public class SpoutCraftEnable implements Listener {
	private MainClass mainClass;
	
	public SpoutCraftEnable(MainClass mainClass) {
		this.mainClass = mainClass;
	}
	
	@EventHandler
	public void onSpoutcraftEnabled(SpoutCraftEnableEvent event) {
		SpoutPlayer player = SpoutManager.getPlayer(event.getPlayer());
		
		String strPClass = mainClass.getConfig().getString("users." + player.getName().toLowerCase() + ".PrimaryClass", "Outsider");
		String strSClass = mainClass.getConfig().getString("users." + player.getName().toLowerCase() + ".SecondaryClass", "None");
		
		ClassType PClass = ClassType.getByName(strPClass);
		ClassType SClass = ClassType.getByName(strSClass);
		
		if (PClass == null || !PClass.getType().equals(ClassType.Type.PRIMARY)) {
			mainClass.getServer().getLogger().severe(player.getName() + " has an invalid or no entry for primary class!");
			mainClass.getServer().getLogger().severe("Defaulting to Outsider class.");
			PClass = ClassType.OUTSIDER;
		}
		
		mainClass.playerClasses.setAllClasses(player.getName(), PClass, SClass);
		mainClass.playerClasses.updateTitle(player);
		
		String cape = mainClass.getConfig().getString("users." + player.getName().toLowerCase() + ".CurrentCape", "none");
		
		if (!cape.equals("none") && !mainClass.getSettings().getResourceLocation().equals("err") && player.hasPermission("toybox.capes." + cape))
			player.setCape(mainClass.getSettings().getResourceLocation() + cape + ".png");
		
		if (mainClass.getDescription().getAuthors().contains(player.getName())) {
			player.addAccessory(AccessoryType.BRACELET, "http://www.almuramc.com/playerplus/bracelet/pp-bc-618.png");
		}
	}
}
