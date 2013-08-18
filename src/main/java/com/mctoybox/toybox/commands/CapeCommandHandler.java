package com.mctoybox.toybox.commands;

import org.bukkit.ChatColor;
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
	}
	
	private void DenyCape(CommandSender sender, Command command, String[] args) {
		// cape, player
		SpoutPlayer target;
		String capeName = "";
		
		if (args.length != 2) {
			Message.sendMessage(sender, Message.INVALID_ARGUMENTS);
			sender.sendMessage(command.getUsage());
			return;
		}
		
		capeName = args[0];
		target = (SpoutPlayer) mainClass.getServer().getPlayer(args[1]);
		if (target == null) {
			Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
			return;
		}
		if (!target.hasPermission("toybox.capes." + capeName)) {
			Message.sendMessage(sender, Message.CAPE_NO_PERMISSION_OTHER);
			return;
		}
		target.addAttachment(mainClass, "toybox.capes." + capeName, false);
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
		// If cape == "none" removecape
		
		// Player has permission to use /setcape
		
		SpoutPlayer target;
		boolean removeCape = false, override = false, targIsSender = false;
		String capeName = "";
		
		if (args.length < 1 || args.length > 3) {
			Message.sendMessage(sender, Message.INVALID_ARGUMENTS);
			sender.sendMessage(command.getUsage());
			return;
		}
		
		override = "override".equalsIgnoreCase(args[2]) && sender.hasPermission(Permissions.CAPE_SET_OVERRIDE.getName());
		
		if (args.length == 1) {
			if (!(sender instanceof SpoutPlayer)) {
				Message.sendMessage(sender, Message.SPECIFY_PLAYER);
				return;
			}
			target = (SpoutPlayer) sender;
		}
		else {
			if (!sender.hasPermission(Permissions.CAPE_SET_OTHER.getName())) {
				Message.sendMessage(sender, Message.CAPE_NO_PERMISSION_OTHER);
				return;
			}
			target = (SpoutPlayer) mainClass.getServer().getPlayer(args[1]);
		}
		
		if (target == null) {
			Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
			return;
		}
		
		targIsSender = ((SpoutPlayer) sender).equals(target);
		
		removeCape = "none".equalsIgnoreCase(args[0]);
		capeName = args[0];
		
		if (removeCape) {
			target.resetCape();
			Message.sendMessage(target, ChatColor.GREEN + "Your cape has been removed" + (targIsSender ? "" : " by " + sender.getName()) + "!");
			Message.sendMessage(sender, ChatColor.GREEN + "You have removed " + target.getName() + "'s cape!");
			mainClass.logMessage(sender.getName() + " removed " + target.getName() + "'s cape.");
		}
		else {
			if (target.hasPermission("toybox.capes." + capeName) || override) {
				target.setCape(mainClass.getSettings().getResourceLocation() + capeName + ".png");
				Message.sendMessage(target, ChatColor.GREEN + "Your cape was set to " + capeName + (targIsSender ? "" : " by " + sender.getName()) + "!");
				Message.sendMessage(sender, ChatColor.GREEN + "You have set " + target.getName() + "'s cape to " + capeName + "!");
				mainClass.logMessage(sender.getName() + " set " + target.getName() + "'s cape to " + capeName + ".");
			}
			else {
				Message.sendMessage(sender, Message.CAPE_NO_PERMISSION_OTHER);
			}
		}
		
	}
}
