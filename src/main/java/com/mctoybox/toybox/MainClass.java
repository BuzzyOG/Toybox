package com.mctoybox.toybox;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

import com.mctoybox.toybox.blocks.ColoredGlass;
import com.mctoybox.toybox.blocks.Plaster;
import com.mctoybox.toybox.blocks.Stove;
import com.mctoybox.toybox.classes.ClassList;
import com.mctoybox.toybox.classes.ClassType;
import com.mctoybox.toybox.commands.CapeCommandHandler;
import com.mctoybox.toybox.commands.ClassCommandHandler;
import com.mctoybox.toybox.items.Catalyst;
import com.mctoybox.toybox.listeners.PlayerInteract;
import com.mctoybox.toybox.listeners.PlayerMove;
import com.mctoybox.toybox.listeners.SpoutCraftEnable;
import com.mctoybox.toybox.util.ExtendedHashMap;
import com.mctoybox.toybox.util.ItemChecker;
import com.mctoybox.toybox.util.Recipes;
import com.mctoybox.toybox.util.Settings;
import com.mctoybox.toybox.util.holder.ClassHolder;
import com.mctoybox.toybox.util.holder.MaterialHolder;
import com.mctoybox.toybox.util.holder.RecipeHolder;

public class MainClass extends JavaPlugin {
	public ExtendedHashMap<String, ClassHolder> playerClasses;
	public ClassList classList;
	
	private Settings settings;
	
	private String[] colours = new String[] { "White", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
	private Material[] dyeArray = { MaterialData.inkSac, MaterialData.lapisLazuli, MaterialData.cocoaBeans, MaterialData.cyanDye, MaterialData.grayDye, MaterialData.cactusGreen,
			MaterialData.lightBlueDye, MaterialData.lightGrayDye, MaterialData.limeDye, MaterialData.magentaDye, MaterialData.orangeDye, MaterialData.pinkDye, MaterialData.purpleDye,
			MaterialData.roseRed, MaterialData.boneMeal, MaterialData.dandelionYellow };
	
	public void onEnable() {
		saveDefaultConfig();
		
		settings = new Settings(this);
		
		classList = new ClassList(this);
		
		CopyResources();
		
		CreateBlocks();
		CreateCommands();
		CreateItems();
		
		settings.setupLater();
		
		CreateRecipes();
		
		playerClasses = new ExtendedHashMap<String, ClassHolder>();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new SpoutCraftEnable(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new PlayerMove(this), this);
	}
	
	private void CopyResources() {
		saveResource("Resources" + File.separatorChar + "Blocks" + File.separatorChar + "Stove" + File.separatorChar + "stove_front_on.png", true);
		for (String s : colours) {
			saveResource("Resources" + File.separatorChar + "Blocks" + File.separatorChar + "Plaster" + File.separatorChar + "BPlaster" + s + ".png", true);
			saveResource("Resources" + File.separatorChar + "Blocks" + File.separatorChar + "Glass" + File.separatorChar + "BGlass" + s + ".png", true);
			saveResource("Resources" + File.separatorChar + "Items" + File.separatorChar + "ColouredBricks" + File.separatorChar + "IBrick" + s + ".png", true);
		}
	}
	
	private void CreateBlocks() {
		
		new Stove(this);
		
		for (String s : colours) {
			new Plaster(this, s);
			
		}
		// TODO light blue glass needs darkeningd
		for (String s : colours) {
			new ColoredGlass(this, s);
		}
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
		Recipes.NewRecipe(new RecipeHolder(ItemChecker.LookupItem(this, "Stove"), "000010000", MaterialData.cobblestone, MaterialData.glass));
		Recipes.NewRecipe(new RecipeHolder(ItemChecker.LookupItem(this, "Stove"), "000010000", MaterialData.cobblestone, MaterialData.glassPane));
		
		// All plasters
		
		for (int i = 0; i < colours.length; i++) {
			Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(colours[i] + " plaster"), MaterialData.sand, MaterialData.clay, dyeArray[i]));
			for (int j = 0; j < colours.length; j++) {
				if (i == j)
					continue;
				Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(colours[i] + " plaster"), new MaterialHolder(colours[j] + " plaster"), new MaterialHolder(dyeArray[i])));
			}
		}
		
		/*
		 * Items
		 */
		// Catalyst
		Recipes.NewRecipe(new RecipeHolder(ItemChecker.LookupItem(this, "catalyst"), "010121010", ClassType.ALCHEMIST, MaterialData.obsidian, MaterialData.glowstoneDust, MaterialData.diamond));
		Recipes.NewRecipe(new RecipeHolder(ItemChecker.LookupItem(this, "catalyst"), "010121010", ClassType.ALCHEMIST, MaterialData.glowstoneDust, MaterialData.obsidian, MaterialData.diamond));
		
		// Rotten Flesh -> Leather
		Recipes.NewRecipe(new RecipeHolder(MaterialData.leather, "furnace", MaterialData.rottenFlesh));
		
		/*
		 * Transmutions
		 */
		// 8 cobblestone -> 1 iron ingot
		Recipes.NewRecipe(new RecipeHolder(MaterialData.ironIngot, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.cobblestone, 8)));
		// 8 iron ingot -> 1 gold ingot
		Recipes.NewRecipe(new RecipeHolder(MaterialData.goldIngot, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.ironIngot, 8)));
		// 8 iron block -> 1 gold block
		Recipes.NewRecipe(new RecipeHolder(MaterialData.goldBlock, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.ironBlock, 8)));
		// 8 gold ingot -> 1 diamond
		Recipes.NewRecipe(new RecipeHolder(MaterialData.diamond, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.goldIngot, 8)));
		// 8 gold block -> 1 diamond block
		Recipes.NewRecipe(new RecipeHolder(MaterialData.diamondBlock, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.goldBlock, 8)));
		// 4 charcoal -> 1 coal
		Recipes.NewRecipe(new RecipeHolder(MaterialData.coal, ClassType.ALCHEMIST, new MaterialHolder(getSettings().getUpgradeCatalyst()), new MaterialHolder(MaterialData.charcoal, 4)));
		
		// 1 iron ingot -> 8 cobblestone
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.cobblestone, 8), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.ironIngot));
		// 1 gold bar -> 8 iron ingot
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.ironIngot, 8), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.goldIngot));
		// 1 gold block -> 8 iron block
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.ironBlock, 8), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.goldBlock));
		// 1 diamond -> 8 gold bar
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.goldIngot, 8), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.diamond));
		// 1 diamond block -> 8 gold block
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.goldBlock, 8), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.diamondBlock));
		// 1 coal -> 4 charcoal
		Recipes.NewRecipe(new RecipeHolder(new MaterialHolder(MaterialData.charcoal, 4), ClassType.ALCHEMIST, getSettings().getDowngradeCatalyst(), MaterialData.coal));
		
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public void logMessage(Object message) {
		getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Toybox] " + String.valueOf(message));
	}
	
	public void debugOutput(Object message) {
		debugOutput(String.valueOf(message));
	}
	
	public void debugOutput(String message) {
		if (getSettings().getDebugMode())
			getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Debug] " + message);
	}
}
