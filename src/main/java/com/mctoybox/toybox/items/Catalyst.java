package com.mctoybox.toybox.items;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.material.item.GenericCustomItem;

import com.mctoybox.toybox.MainClass;

public class Catalyst extends GenericCustomItem {
	private MainClass mainClass;
	
	public Catalyst(Plugin plugin) {
		super(plugin, "Catalyst");
		this.mainClass = (MainClass) plugin;
		this.setStackable(false);
		
		MaterialData.addCustomItem(this);
		
		mainClass.debugOutput("Catalyst item initialized!");
	}
}
