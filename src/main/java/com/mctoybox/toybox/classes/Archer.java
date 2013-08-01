package com.mctoybox.toybox.classes;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.Permissions;

public class Archer extends ClassBase {
	
	public Archer(MainClass mainClass) {
		super(mainClass, "Archer", Permissions.CLASSES_ARCHER);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		mainClass.debugOutput("EntityDamageByEntityEvent triggered in Archer.java");
		if (!(event.getDamager().getType().equals(EntityType.ARROW)))
			return;
		mainClass.debugOutput("Damager is an arrow!");
		mainClass.debugOutput("Damage precalc: " + event.getDamage());
		
		SpoutPlayer player = (SpoutPlayer) ((Arrow) event.getDamager()).getShooter();
		
		if (player == null)
			return;
		mainClass.debugOutput("Arrow fired by player!");
		
		if (!mainClass.playerClasses.getPrimaryClass(player).equals(classRef))
			return;
		mainClass.debugOutput("Damager is an archer!");
		
		if (!event.getCause().equals(DamageCause.PROJECTILE))
			return;
		mainClass.debugOutput("Damage caused by a projectile!");
		
		if (event.getEntity() instanceof SpoutPlayer) {
			mainClass.debugOutput("Damaged entity is a player!");
		}
		else {
			mainClass.debugOutput("Damaged entity is not a player!");
			
			event.setDamage(event.getDamage() + (event.getDamage() / 4));
			mainClass.debugOutput("Damage postcalc: " + event.getDamage());
		}
	}
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		mainClass.debugOutput("EntityShootBowEvent triggered in Archer.java");
		
		if (!(event.getEntity() instanceof SpoutPlayer))
			return;
		mainClass.debugOutput("Entity is a player!");
		
		ItemStack bow = event.getBow();
		bow.setDurability((short) (bow.getDurability() + 1));
		
		SpoutPlayer player = (SpoutPlayer) event.getEntity();
		
		if (!mainClass.playerClasses.getPrimaryClass(player).equals(classRef))
			return;
		mainClass.debugOutput("Entity is an archer!");
		
		if (Math.random() > 0.5) {
			mainClass.debugOutput("Random test passed, repairing bow slightly.");
			bow.setDurability((short) (bow.getDurability() - 1));
			player.sendNotification("", "", Material.BOW);
			player.sendMessage("Your skill in archery prevents your bow being damaged!");
		}
		else {
			mainClass.debugOutput("Random test failed, not repairing bow.");
		}
	}
}
