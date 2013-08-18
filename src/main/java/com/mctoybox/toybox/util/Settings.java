package com.mctoybox.toybox.util;

import java.util.ArrayList;
import java.util.List;

import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

import com.mctoybox.toybox.MainClass;

public class Settings {
	private MainClass mainClass;
	private String resourceLocation;
	private List<String> capeList = new ArrayList<String>();
	private Material downgradeCatalyst;
	private Material upgradeCatalyst;
	private boolean debugMode;
	
	public Settings(MainClass mainClass) {
		this.mainClass = mainClass;
		setup();
	}
	
	public void clear() {
		capeList.clear();
		resourceLocation = "";
		downgradeCatalyst = null;
		upgradeCatalyst = null;
	}
	
	public void setup() {
		capeList = mainClass.getConfig().getStringList(Config.CAPE_LIST.toString());
		debugMode = mainClass.getConfig().getBoolean("config.debug", false);
		for (int i = 0; i < capeList.size(); i++) {
			capeList.set(i, capeList.get(i).toLowerCase()); // lowercase errything
		}
		resourceLocation = mainClass.getConfig().getString(Config.RESOURCE_LOCATION.toString(), "err");
		
		mainClass.logMessage("Configuration setup!");
	}
	
	public void setupLater() {
		downgradeCatalyst = ItemChecker.LookupItem(mainClass, mainClass.getConfig().getString(Config.ALCHEMIST_DOWNGRADE_CATALYST.toString()));
		if (downgradeCatalyst == null) {
			downgradeCatalyst = MaterialData.feather;
		}
		upgradeCatalyst = ItemChecker.LookupItem(mainClass, mainClass.getConfig().getString(Config.ALCHEMIST_UPGRADE_CATALYST.toString()));
		if (upgradeCatalyst == null) {
			upgradeCatalyst = MaterialData.slimeball;
		}
	}
	
	public Material getUpgradeCatalyst() {
		return upgradeCatalyst;
	}
	
	public Material getDowngradeCatalyst() {
		return downgradeCatalyst;
	}
	
	public List<String> getCapeList() {
		return capeList;
	}
	
	public String getResourceLocation() {
		return resourceLocation;
	}
	
	public boolean getDebugMode() {
		return debugMode;
	}
}
