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
import org.getspout.spoutapi.material.MaterialData;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.ItemChecker;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;
import com.mctoybox.toybox.util.holder.RecipeHolder;

public class Alchemist extends ClassBase {
	private ArrayList<PotionEffectType> helpfulPotions;
	
	public Alchemist(MainClass mainClass) {
		super(mainClass, "Alchemist", Permissions.CLASSES_ALCHEMIST);
		
		helpfulPotions = new ArrayList<PotionEffectType>();
		PotionEffectType[] types = { PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.FAST_DIGGING, PotionEffectType.FIRE_RESISTANCE, PotionEffectType.INCREASE_DAMAGE,
				PotionEffectType.INVISIBILITY, PotionEffectType.JUMP, PotionEffectType.NIGHT_VISION, PotionEffectType.REGENERATION, PotionEffectType.SPEED, PotionEffectType.WATER_BREATHING };
		
		for (PotionEffectType t : types) {
			helpfulPotions.add(t);
		}
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
		
		RecipeHolder foundRecipe = ItemChecker.GetToyboxRecipe(craftWindow);
		if (foundRecipe == null) {
			return;
		}
		if (!classRef.equals(foundRecipe.getClassRestriction())) {
			return;
		}
		
		boolean AlchemistRecipe = true;
		if (!classRef.equals(mainClass.playerClasses.getSecondaryClass(player))) {
			event.setCancelled(true);
			for (int i = 1; i < craftWindow.length; i++) {
				player.getInventory().addItem(craftWindow[i]);
				craftWindow[i] = null;
			}
			player.closeInventory();
			Message.sendMessage(player, Message.CLASS_ALCHEMIST_ONLY_TRANSMUTE);
		}
		
		// TODO fix this up properly
		if (!event.isCancelled() && AlchemistRecipe) {
			if (ItemChecker.CraftingArrayHas(mainClass, craftWindow, mainClass.getSettings().getDowngradeCatalyst())) {
				player.getInventory().addItem(new SpoutItemStack(mainClass.getSettings().getDowngradeCatalyst(), 1));
			}
			else {
				player.getInventory().addItem(new SpoutItemStack(mainClass.getSettings().getUpgradeCatalyst(), 1));
			}
		}
	}
	
	@Override
	public void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException {
		if (!player.hasPermission(permRequired.getName())) {
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
