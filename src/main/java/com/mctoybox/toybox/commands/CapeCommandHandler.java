package com.mctoybox.toybox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class CapeCommandHandler extends CommandHandler {
	public CapeCommandHandler(MainClass mainClass) {
		super(mainClass);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName().toLowerCase()) {
			case "cape":
				Cape(sender, command, args);
				break;
			case "denycape":
				DenyCape(sender, command, args);
				break;
			case "grantcape":
				GrantCape(sender, command, args);
				break;
			case "listallcape":
				ListAllCapes(sender, command, args);
				break;
			case "listcape":
				ListCapes(sender, command, args);
				break;
			case "setcape":
				SetCape(sender, command, args);
				break;
			default:
				Message.sendMessage(sender, Message.UNKNOWN_COMMAND);
				return false;
		}
		return true;
	}
	
	private void Cape(CommandSender sender, Command command, String[] args) {
		sender.sendMessage(command.getUsage());
		return;
	}
	
	private void DenyCape(CommandSender sender, Command command, String[] args) {
		return;
	}
	
	private void GrantCape(CommandSender sender, Command command, String[] args) {
		return;
	}
	
	private void ListCapes(CommandSender sender, Command command, String[] args) {
		return;
	}
	
	private void ListAllCapes(CommandSender sender, Command command, String[] args) {
		return;
	}
	
	private void SetCape(CommandSender sender, Command command, String[] args) {
		// setcape cape
		// setcape cape player
		// setcape cape player override
		
		if (args.length == 0 || args.length > 3) {
			sender.sendMessage(command.getUsage());
			return;
		}
		if (!mainClass.capes.contains(args[0])) {
			Message.sendMessage(sender, Message.CAPE_NOT_FOUND);
			Message.sendMessage(sender, Message.CAPE_NOT_FOUND_NOTE);
			return;
		}
		SpoutPlayer target;
		if (!(sender instanceof SpoutPlayer) && args.length == 1) {
			Message.sendMessage(sender, Message.SPECIFY_PLAYER);
			return;
		}
		else if (sender instanceof SpoutPlayer && args.length == 1) {
			target = (SpoutPlayer) sender;
		}
		else {
			target = (SpoutPlayer) mainClass.getServer().getPlayer(args[1]);
			if (target == null) {
				Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
				sender.sendMessage(command.getUsage());
				return;
			}
			if (!sender.hasPermission(Permissions.CAPE_SET_OTHER)) {
				Message.sendMessage(sender, Message.NO_PERM_SET_OTHER_CAPE);
			}
		}
		boolean override = false;
		
		if ("override".equalsIgnoreCase(args[2])) {
			if (!sender.hasPermission(Permissions.CAPE_SET_OVERRIDE)) {
				Message.sendMessage(sender, Message.NO_PERM_OVERRIDE_CAPE);
				return;
			}
			override = true;
		}
		
		if (!override && !mainClass.getConfig().getStringList("user." + target.getName() + ".AllowedCapes").contains(args[0])) {
			Message.sendMessage(sender, sender.getName().equals(target.getName()) ? Message.CAPE_NO_PERMISSION : Message.CAPE_NO_PERMISSION_OTHER);
			sender.sendMessage(command.getUsage());
			return;
		}
		
		target.setCape(mainClass.capeLocation.getAbsolutePath() + args[0] + ".png");
		Message.sendMessage(target, Message.CAPE_SET);
		if (!sender.getName().equals(target.getName())) {
			Message.sendMessage(sender, Message.CAPE_SET_OTHER);
		}
	}
}
