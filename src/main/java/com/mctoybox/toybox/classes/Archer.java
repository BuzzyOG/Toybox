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
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class Archer extends ClassBase {
	
	public Archer(MainClass mainClass) {
		super(mainClass, "Archer", Permissions.CLASSES_ARCHER);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager().getType().equals(EntityType.ARROW)))
			return;
		
		SpoutPlayer player = (SpoutPlayer) ((Arrow) event.getDamager()).getShooter();
		
		if (player == null)
			return;
		
		if (!mainClass.playerClasses.getPrimaryClass(player).equals(classRef))
			return;
		
		if (!event.getCause().equals(DamageCause.PROJECTILE))
			return;
		
		if (!(event.getEntity() instanceof SpoutPlayer)) {
			event.setDamage(event.getDamage() + (event.getDamage() / 4));
		}
	}
	
	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof SpoutPlayer))
			return;
		
		ItemStack bow = event.getBow();
		bow.setDurability((short) (bow.getDurability() + 1));
		
		SpoutPlayer player = (SpoutPlayer) event.getEntity();
		
		if (!classRef.equals(mainClass.playerClasses.getPrimaryClass(player)))
			return;
		
		if (Math.random() > 0.5) {
			bow.setDurability((short) (bow.getDurability() - 1));
			player.sendNotification("", "", Material.BOW);
			Message.sendMessage(player, Message.CLASS_ARCHER_BOW_REPAIR);
		}
	}
}
