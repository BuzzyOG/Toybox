package com.mctoybox.plugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.player.accessories.AccessoryType;

import com.mctoybox.plugin.MainClass;
import com.mctoybox.plugin.classes.ClassTypes;

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
		
		ClassTypes PClass = ClassTypes.getByName(strPClass);
		ClassTypes SClass = ClassTypes.getByName(strSClass);
		
		if (PClass == null || !PClass.getType().equals(ClassTypes.Type.PRIMARY)) {
			mainClass.getServer().getLogger().severe(player.getName() + " has an invalid or no entry for primary class!");
			mainClass.getServer().getLogger().severe("Defaulting to Outsider class.");
			PClass = ClassTypes.OUTSIDER;
		}
		
		mainClass.playerClasses.setAllClasses(player.getName(), PClass, SClass);
		mainClass.playerClasses.updateTitle(player);
		
		String cape = mainClass.getConfig().getString("users." + player.getName().toLowerCase() + ".CurrentCape", "none");
		
		if (!cape.equals("none") && !mainClass.capeLocation.equals("/path/to/cape/directory/"))
			player.setCape(mainClass.capeLocation + cape + ".png");
		
		if (mainClass.getDescription().getAuthors().contains(player.getName())) {
			player.addAccessory(AccessoryType.TOPHAT, "http://www.almuramc.com/playerplus/tophat/pp-th-23.png");
		}
	}
}
