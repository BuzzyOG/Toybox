package com.mctoybox.toybox.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class Warrior extends ClassBase {
	
	private List<Material> allowedTools = new ArrayList<Material>();
	private List<Material> allowedArmour = new ArrayList<Material>();
	
	public Warrior(MainClass mainClass) {
		super(mainClass, "Warrior", Permissions.CLASSES_WARRIOR);
		
		this.allowedTools.add(Material.STONE_SWORD);
		this.allowedTools.add(Material.IRON_SWORD);
		this.allowedTools.add(Material.GOLD_SWORD);
		this.allowedTools.add(Material.DIAMOND_SWORD);
		
		this.allowedArmour.add(Material.IRON_HELMET);
		this.allowedArmour.add(Material.IRON_CHESTPLATE);
		this.allowedArmour.add(Material.IRON_LEGGINGS);
		this.allowedArmour.add(Material.IRON_BOOTS);
		this.allowedArmour.add(Material.GOLD_HELMET);
		this.allowedArmour.add(Material.GOLD_CHESTPLATE);
		this.allowedArmour.add(Material.GOLD_LEGGINGS);
		this.allowedArmour.add(Material.GOLD_BOOTS);
		this.allowedArmour.add(Material.DIAMOND_HELMET);
		this.allowedArmour.add(Material.DIAMOND_CHESTPLATE);
		this.allowedArmour.add(Material.DIAMOND_LEGGINGS);
		this.allowedArmour.add(Material.DIAMOND_BOOTS);
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
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof SpoutPlayer) {
			SpoutPlayer player = (SpoutPlayer) event.getEntity();
			if (mainClass.playerClasses.get(player.getName()).getPrimary().equals(classRef)) {
				switch (event.getCause()) {
					case ENTITY_ATTACK:
					case PROJECTILE:
					case ENTITY_EXPLOSION:
					case THORNS:
					case CUSTOM:
						event.setDamage(event.getDamage() - (event.getDamage() / 4));
						break;
					default:
						break;
				}
			}
		}
	}
}
