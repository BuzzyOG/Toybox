package com.mctoybox.plugin.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.plugin.MainClass;
import com.mctoybox.plugin.util.Vector3D;

public class PlayerMove implements Listener {
	private MainClass mainClass;
	
	public PlayerMove(MainClass mainClass) {
		this.mainClass = mainClass;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		for (Player p : mainClass.getServer().getOnlinePlayers()) {
			SpoutPlayer player = (SpoutPlayer) p;
			
			Location playerPos = player.getEyeLocation();
			Vector3D playerDir = new Vector3D(playerPos.getDirection());
			
			Vector3D playerStart = new Vector3D(playerPos);
			Vector3D playerEnd = playerStart.add(playerDir.multiply(30));
			
			for (Player target : player.getWorld().getPlayers()) {
				Vector3D targetPos = new Vector3D(target.getLocation());
				Vector3D minimum = targetPos.add(-0.5, 0, -0.5);
				Vector3D maximum = targetPos.add(0.5, 1.67, 0.5);
				
				if (target != player && hasIntersection(playerStart, playerEnd, minimum, maximum)) {
					BlockIterator iterator = new BlockIterator(playerPos, 0, (int) player.getLocation().distance(target.getLocation()));
					boolean blocked = false;
					while (iterator.hasNext()) {
						Block block = iterator.next();
						if (block.getType().isOccluding()) {
							blocked = true;
							break;
						}
					}
					if (blocked)
						continue;
					((SpoutPlayer) target).setTitleFor(player, mainClass.playerClasses.getTitle((SpoutPlayer) target));
				}
				else if (target != player && !hasIntersection(playerStart, playerEnd, minimum, maximum)) {
					((SpoutPlayer) target).hideTitleFrom(player);
				}
			}
		}
	}
	
	private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
		final double epsilon = 0.0001f;
		
		Vector3D d = p2.subtract(p1).multiply(0.5);
		Vector3D e = max.subtract(min).multiply(0.5);
		Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5));
		Vector3D ad = d.abs();
		
		if (Math.abs(c.x) > e.x + ad.x)
			return false;
		if (Math.abs(c.y) > e.y + ad.y)
			return false;
		if (Math.abs(c.z) > e.z + ad.z)
			return false;
		
		if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + epsilon)
			return false;
		if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + epsilon)
			return false;
		if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + epsilon)
			return false;
		
		return true;
	}
}
