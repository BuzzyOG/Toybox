package com.mctoybox.toybox.util;

public enum Config {
	RESOURCE_LOCATION("config.externalResourcePath"),
	CAPE_LIST("config.capeList"),
	
	ALCHEMIST_CATALYST_USES("classes.alchemist.catalystUses"),
	ALCHEMIST_DOWNGRADE_CATALYST("classes.alchemist.downgradeCatalyst"),
	ALCHEMIST_UPGRADE_CATALYST("classes.alchemist.upgradeCatalyst"),
	
	ALCHEMIST_RETURN_DOWNGRADE("classes.alchemist.returnDowngradeCatalyst"),
	ALCHEMIST_RETURN_UPGRADE("classes.alchemist.returnUpgradeCatalyst");
	
	private String path;
	
	private Config(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return path;
	}
}
