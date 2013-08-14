package com.mctoybox.toybox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

import com.mctoybox.toybox.blocks.Plaster;
import com.mctoybox.toybox.blocks.Stove;
import com.mctoybox.toybox.classes.ClassList;
import com.mctoybox.toybox.commands.CapeCommandHandler;
import com.mctoybox.toybox.commands.ClassCommandHandler;
import com.mctoybox.toybox.items.Catalyst;
import com.mctoybox.toybox.listeners.PlayerInteract;
import com.mctoybox.toybox.listeners.PlayerMove;
import com.mctoybox.toybox.listeners.SpoutCraftEnable;
import com.mctoybox.toybox.util.ClassHolder;
import com.mctoybox.toybox.util.Config;
import com.mctoybox.toybox.util.ExtendedHashMap;
import com.mctoybox.toybox.util.ItemChecker;
import com.mctoybox.toybox.util.MaterialHolder;
import com.mctoybox.toybox.util.Recipes;

public class MainClass extends JavaPlugin {
	
	public ExtendedHashMap<String, ClassHolder> playerClasses;
	
	public ClassList classList;
	
	private boolean debugMode;
	public File capeLocation;
	public List<String> capes;
	
	public String externalPath;
	
	public void onEnable() {
		saveDefaultConfig();
		
		debugMode = getConfig().getBoolean("config.debug", false);
		externalPath = getConfig().getString(Config.CAPE_EXTERNAL_LOCATION.toString());
		
		classList = new ClassList(this);
		
		CreateBlocks();
		CreateCommands();
		CreateItems();
		
		CreateRecipes();
		
		playerClasses = new ExtendedHashMap<String, ClassHolder>();
		
		capeLocation = new File(getConfig().getString("config.capePath", "/path/to/cape/directory/"));
		capes = new ArrayList<String>();
		if (capeLocation.canRead())
			for (File f : capeLocation.listFiles()) {
				if (f.isFile()) {
					capes.add(f.getName().substring(0, f.getName().toLowerCase().lastIndexOf(".")));
				}
			}
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SpoutCraftEnable(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new PlayerMove(this), this);
	}
	
	public void onDisable() {
	}
	
	private void CreateBlocks() {
		new Stove(this);
		
		new Plaster(this, "Black");
		new Plaster(this, "Blue");
		new Plaster(this, "Brown");
		new Plaster(this, "Cyan");
		new Plaster(this, "Gray");
		new Plaster(this, "Green");
		new Plaster(this, "Light Blue");
		new Plaster(this, "Light Gray");
		new Plaster(this, "Lime");
		new Plaster(this, "Magenta");
		new Plaster(this, "Orange");
		new Plaster(this, "Pink");
		new Plaster(this, "Purple");
		new Plaster(this, "Red");
		new Plaster(this, "White");
		new Plaster(this, "Yellow");
	}
	
	private void CreateCommands() {
		CommandExecutor capeCommands = new CapeCommandHandler(this);
		CommandExecutor classCommands = new ClassCommandHandler(this);
		
		getCommand("cape").setExecutor(capeCommands);
		getCommand("denycape").setExecutor(capeCommands);
		getCommand("grantcape").setExecutor(capeCommands);
		getCommand("listcape").setExecutor(capeCommands);
		getCommand("listallcape").setExecutor(capeCommands);
		getCommand("setcape").setExecutor(capeCommands);
		
		getCommand("class").setExecutor(classCommands);
	}
	
	private void CreateItems() {
		new Catalyst(this);
	}
	
	private void CreateRecipes() {
		/*
		 * Blocks
		 */
		// Stove
		Recipes.NewShapedRecipe(new MaterialHolder(ItemChecker.LookupItem(getName(), "Stove")), "000010000", MaterialData.cobblestone, MaterialData.glass);
		Recipes.NewShapedRecipe(new MaterialHolder(ItemChecker.LookupItem(getName(), "Stove")), "000010000", MaterialData.cobblestone, MaterialData.glassPane);
		
		String[] plasterArray = { "black", "blue", "brown", "cyan", "gray", "green", "light blue", "light gray", "lime", "magenta", "orange", "pink", "purple", "red", "white", "yellow" };
		Material[] dyeArray = { MaterialData.inkSac, MaterialData.lapisLazuli, MaterialData.cocoaBeans, MaterialData.cyanDye, MaterialData.grayDye, MaterialData.cactusGreen,
				MaterialData.lightBlueDye, MaterialData.lightGrayDye, MaterialData.limeDye, MaterialData.magentaDye, MaterialData.orangeDye, MaterialData.pinkDye, MaterialData.purpleDye,
				MaterialData.roseRed, MaterialData.boneMeal, MaterialData.dandelionYellow };
		
		// All plasters
		Recipes.NewShapelessRecipe(new MaterialHolder("white plaster"), MaterialData.sand, MaterialData.clay);
		for (int i = 0; i < plasterArray.length; i++) {
			if (!plasterArray[i].equals("white")) {
				Recipes.NewShapelessRecipe(new MaterialHolder(plasterArray[i] + " plaster"), MaterialData.sand, MaterialData.clay, dyeArray[i]);
			}
			for (int j = 0; j < plasterArray.length; j++) {
				if (i == j)
					continue;
				Recipes.NewShapelessRecipe(new MaterialHolder(plasterArray[i] + " plaster"), new MaterialHolder(plasterArray[j] + " plaster"), new MaterialHolder(dyeArray[i]));
			}
		}
		
		/*
		 * Items
		 */
		// Catalyst
		Recipes.NewShapedRecipe(new MaterialHolder(ItemChecker.LookupItem(getName(), "catalyst")), "010121010", MaterialData.obsidian, MaterialData.glowstoneDust, MaterialData.diamond);
		Recipes.NewShapedRecipe(new MaterialHolder(ItemChecker.LookupItem(getName(), "catalyst")), "010121010", MaterialData.glowstoneDust, MaterialData.obsidian, MaterialData.diamond);
	}
	
	public void logMessage(Object message) {
		getServer().getConsoleSender().sendMessage(String.valueOf(message));
	}
	
	public void debugOutput(Object message) {
		debugOutput(String.valueOf(message));
	}
	
	public void debugOutput(String message) {
		if (debugMode)
			getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Debug] " + message);
	}
}
