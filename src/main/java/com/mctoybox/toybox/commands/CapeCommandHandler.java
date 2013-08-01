package com.mctoybox.toybox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.Permissions;
import com.mctoybox.toybox.util.Message;

public class CapeCommandHandler extends CommandHandler implements CommandExecutor {
	public CapeCommandHandler(MainClass mainClass) {
		super(mainClass, "cape");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String[] toPass = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			toPass[i - 1] = args[i];
		}
		
		if ("set".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_SET_OWN))
				return SetCape(sender, toPass);
			else
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
		}
		else if ("list".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_LIST))
				ListCapes(sender, toPass);
			else
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
		}
		else if ("grant".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_GRANT))
				GrantCape(sender, toPass);
			else
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
		}
		else if ("deny".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_DENY))
				DenyCape(sender, toPass);
			else
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
		}
		else {
			Message.sendMessage(sender, Message.UNKNOWN_COMMAND);
			sender.sendMessage(getUsage());
		}
		return true;
	}
	
	private void DenyCape(CommandSender sender, String[] args) {
		
	}
	
	private void GrantCape(CommandSender sender, String[] args) {
		
	}
	
	private void ListCapes(CommandSender sender, String[] args) {
		
	}
	
	private boolean SetCape(CommandSender sender, String[] args) {
		// cape set cape |args = { cape }
		// cape set cape player |args = { cape, player }
		// cape set cape player override |args = { cape, player, override }
		
		if (args.length == 0 || args.length > 3) {
			sender.sendMessage(getDescription());
			return false;
		}
		if (!mainClass.capes.contains(args[0])) {
			Message.sendMessage(sender, Message.CAPE_NOT_FOUND);
			Message.sendMessage(sender, Message.CAPE_NOT_FOUND_NOTE);
			return true;
		}
		SpoutPlayer target;
		if (!(sender instanceof SpoutPlayer) && args.length == 1) {
			Message.sendMessage(sender, Message.SPECIFY_PLAYER);
			return false;
		}
		else if (sender instanceof SpoutPlayer && args.length == 1) {
			target = (SpoutPlayer) sender;
		}
		else {
			target = (SpoutPlayer) mainClass.getServer().getPlayer(args[1]);
			if (target == null) {
				Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
				return false;
			}
			if (!sender.hasPermission(Permissions.CAPE_SET_OTHER)) {
				Message.sendMessage(sender, Message.NO_PERM_SET_OTHER_CAPE);
			}
		}
		boolean override = false;
		
		if ("override".equalsIgnoreCase(args[2])) {
			if (!sender.hasPermission(Permissions.CAPE_SET_OVERRIDE)) {
				Message.sendMessage(sender, Message.NO_PERM_OVERRIDE_CAPE);
				return true;
			}
			override = true;
		}
		
		if (!override && !mainClass.getConfig().getStringList("user." + target.getName() + ".AllowedCapes").contains(args[0])) {
			Message.sendMessage(sender, sender.getName().equals(target.getName()) ? Message.CAPE_NO_PERMISSION : Message.CAPE_NO_PERMISSION_OTHER);
			return false;
		}
		
		target.setCape(mainClass.capeLocation.getAbsolutePath() + args[0] + ".png");
		Message.sendMessage(target, Message.CAPE_SET);
		if (!sender.getName().equals(target.getName())) {
			Message.sendMessage(sender, Message.CAPE_SET_OTHER);
		}
		return true;
	}
}
