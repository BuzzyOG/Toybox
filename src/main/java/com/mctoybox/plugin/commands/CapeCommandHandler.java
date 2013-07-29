package com.mctoybox.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.mctoybox.plugin.MainClass;
import com.mctoybox.plugin.Permissions;

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
			if (sender.hasPermission(Permissions.CAPE_SET_OWN) || sender.hasPermission(Permissions.CAPE_SET_OTHER))
				SetCape(sender, toPass);
			else
				sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
		}
		else if ("list".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_LIST) || sender.hasPermission(Permissions.CAPE_LIST_ALL))
				ListCapes(sender, toPass);
			else
				sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
		}
		else if ("grant".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_GRANT))
				GrantCape(sender, toPass);
			else
				sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
		}
		else if ("deny".equalsIgnoreCase(args[0])) {
			if (sender.hasPermission(Permissions.CAPE_DENY))
				DenyCape(sender, toPass);
			else
				sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
		}
		else {
			sender.sendMessage("Unknown command!");
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
	
	private void SetCape(CommandSender sender, String[] args) {
		// cape set cape |args = { cape }
		// cape set cape player |args = { cape, player }
		// cape set cape override |args = { cape, override }
		// cape set cape player override |args = { cape, player, override }
		if (args.length == 0) {
			sender.sendMessage(getDescription());
			sender.sendMessage(getUsage());
			return;
		}
		if (!mainClass.capes.contains(args[0])) {
			sender.sendMessage(ChatColor.RED + "That cape could not be found!");
			return;
		}
		
		boolean override = false;
		if ("override".equalsIgnoreCase(args[args.length - 1])) {
			if (sender.hasPermission(Permissions.CAPE_SET_OVERRIDE))
				override = true;
			else {
				sender.sendMessage(ChatColor.RED + "You don't have permission to override cape permissions.");
				return;
			}
		}
		
	}
	
}
