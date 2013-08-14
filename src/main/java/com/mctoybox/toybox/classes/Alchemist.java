package com.mctoybox.toybox.classes;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.getspout.spoutapi.event.screen.ScreenOpenEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Config;
import com.mctoybox.toybox.util.ItemChecker;
import com.mctoybox.toybox.util.MaterialHolder;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;
import com.mctoybox.toybox.util.Recipes;

public class Alchemist extends ClassBase {
	Material downgradeCatalyst = MaterialData.feather;
	Material upgradeCatalyst = MaterialData.slimeball;
	
	private ArrayList<PotionEffectType> helpfulPotions;
	
	private boolean returnCatalyst;
	
	public Alchemist(MainClass mainClass) {
		super(mainClass, "Alchemist", Permissions.CLASSES_ALCHEMIST);
		
		downgradeCatalyst = ItemChecker.LookupItem(mainClass.getName(), mainClass.getConfig().getString(Config.ALCHEMIST_DOWNGRADE_CATALYST.toString()));
		upgradeCatalyst = ItemChecker.LookupItem(mainClass.getName(), mainClass.getConfig().getString(Config.ALCHEMIST_UPGRADE_CATALYST.toString()));
		
		try {
			// Upgrades
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.ironIngot), new MaterialHolder(upgradeCatalyst), new MaterialHolder(MaterialData.cobblestone, 8));
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.goldIngot), new MaterialHolder(upgradeCatalyst), new MaterialHolder(MaterialData.ironIngot, 8));
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.diamond), new MaterialHolder(upgradeCatalyst), new MaterialHolder(MaterialData.goldIngot, 4));
			// Downgrades
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.cobblestone, 8), new MaterialHolder(downgradeCatalyst), new MaterialHolder(MaterialData.ironIngot));
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.ironIngot, 8), new MaterialHolder(downgradeCatalyst), new MaterialHolder(MaterialData.goldIngot));
			Recipes.NewShapelessRecipe(new MaterialHolder(MaterialData.goldIngot, 4), new MaterialHolder(downgradeCatalyst), new MaterialHolder(MaterialData.diamond));
		}
		catch (IllegalArgumentException e) {
			mainClass.getLogger().severe("Error registering a transmution!");
		}
		helpfulPotions = new ArrayList<PotionEffectType>();
		PotionEffectType[] types = { PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.FAST_DIGGING, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE,
				PotionEffectType.INVISIBILITY, PotionEffectType.JUMP, PotionEffectType.NIGHT_VISION, PotionEffectType.REGENERATION, PotionEffectType.SPEED, PotionEffectType.WATER_BREATHING };
		
		for (PotionEffectType t : types) {
			helpfulPotions.add(t);
		}
		
		returnCatalyst = mainClass.getConfig().getBoolean("classes.alchemist.returnCatalyst", false);
	}
	
	@EventHandler
	public void onScreenOpen(ScreenOpenEvent event) {
		SpoutPlayer player = event.getPlayer();
		if (!event.getScreenType().equals(ScreenType.BREWING_STAND_INVENTORY))
			return;
		if (!classRef.equals(mainClass.playerClasses.getSecondaryClass(player))) {
			event.setCancelled(true);
			Message.sendMessage(player, Message.CLASS_ALCHEMIST_ONLY_BREWING_STAND);
		}
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		ItemStack item = event.getItem();
		if (item == null)
			return;
		
		if (!item.getType().equals(org.bukkit.Material.POTION))
			return;
		
		if (!classRef.equals(mainClass.playerClasses.getSecondaryClass(player)))
			return;
		// If the player is not an alchemist, the potion effect is applied as
		// normal
		
		event.setCancelled(true);
		// We have to cancel the event because otherwise the normal effect is
		// applied, and there's no way to override it
		
		Potion potion = Potion.fromItemStack(item);
		
		player.setItemInHand(new SpoutItemStack(MaterialData.glassBottle));
		PotionEffectType effectType = PotionEffectType.getById(item.getDurability() % 64);
		
		if (helpfulPotions.contains(effectType)) {
			// baseTime and duration are measured in seconds
			int baseTime = (effectType.equals(PotionEffectType.REGENERATION) ? 45 : 180);
			int duration;
			if (potion.getLevel() == 2)
				duration = baseTime / 2;
			else if (potion.hasExtendedDuration())
				duration = (int) (baseTime * 2.6);
			else
				duration = baseTime;
			
			PotionEffect toApply = new PotionEffect(effectType, (int) (duration * 1.5) * 20, potion.getLevel());
			player.addPotionEffect(toApply);
			Message.sendMessage(player, Message.CLASS_ALCHEMIST_BOOST_MESSAGE);
		}
		
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent event) {
		SpoutPlayer player = (SpoutPlayer) event.getWhoClicked();
		ItemStack[] craftWindow = event.getInventory().getContents();
		
		boolean AlchemistRecipe = false;
		if (ItemChecker.CraftingArrayHas(mainClass, craftWindow, this.downgradeCatalyst) || ItemChecker.CraftingArrayHas(mainClass, craftWindow, this.upgradeCatalyst)) {
			if (ItemChecker.CraftingArrayHasMinAmount(mainClass, craftWindow, MaterialData.cobblestone, 8)
					|| ItemChecker.CraftingArrayHasMinAmount(mainClass, craftWindow, MaterialData.ironIngot, 8)
					|| ItemChecker.CraftingArrayHasMinAmount(mainClass, craftWindow, MaterialData.goldIngot, 4)
					|| ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow, MaterialData.diamond, 1)
					|| ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow, MaterialData.goldIngot, 1)
					|| ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow, MaterialData.ironIngot, 1)
					|| (ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow, MaterialData.obsidian, 4)
							&& ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow, MaterialData.glowstoneDust, 4) && ItemChecker.CraftingArrayHasExactAmount(mainClass, craftWindow,
							MaterialData.diamond, 1))) {
				AlchemistRecipe = true;
				if (!ClassType.ALCHEMIST.equals(mainClass.playerClasses.getSecondaryClass(player))) {
					event.setCancelled(true);
					player.closeInventory();
					Message.sendMessage(player, Message.CLASS_ALCHEMIST_ONLY_TRANSMUTE);
				}
			}
		}
		
		if (returnCatalyst && !event.isCancelled() && AlchemistRecipe) {
			if (ItemChecker.CraftingArrayHas(mainClass, craftWindow, this.downgradeCatalyst)) {
				player.getInventory().addItem(new SpoutItemStack(downgradeCatalyst, 1));
			}
			else {
				player.getInventory().addItem(new SpoutItemStack(upgradeCatalyst, 1));
			}
		}
	}
	
	@Override
	public void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException {
		if (!player.hasPermission(permRequired)) {
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
}
