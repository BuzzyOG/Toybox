package com.mctoybox.toybox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.Permissions;

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
	
	private boolean SetCape(CommandSender sender, String[] args) {
		// cape set cape |args = { cape }
		// cape set cape player |args = { cape, player }
		// cape set cape override |args = { cape, override }
		// cape set cape player override |args = { cape, player, override }
		
		if (args.length == 1) {
			if (!mainClass.capes.contains(args[0])) {
				sender.sendMessage(ChatColor.RED + "That cape could not be found!");
				sender.sendMessage(ChatColor.RED + "Use  " + ChatColor.DARK_RED + "/cape list" + ChatColor.RESET + " to see capes you can wear");
				return true;
			}
			
			if (!(sender instanceof SpoutPlayer)) {
				sender.sendMessage(ChatColor.RED + "You need to be a player to do that!");
				return false;
			}
			SpoutPlayer player = (SpoutPlayer) sender;
			
			if (!mainClass.getConfig().getStringList("users." + player.getName() + ".AllowedCapes").contains(args[0])) {
				player.sendMessage(ChatColor.RED + "You can't wear that cape!");
				return true;
			}
			
			player.setCape(mainClass.capeLocation.getAbsolutePath() + args[0] + ".png");
			player.sendMessage("Your cape has been set to " + args[0] + "!");
			return true;
		}
		else if (args.length == 2) {
			if (!mainClass.capes.contains(args[0])) {
				sender.sendMessage(ChatColor.RED + "That cape could not be found!");
				return true;
			}
			if (args[1].equalsIgnoreCase("override")) {
				if (sender.hasPermission(Permissions.CAPE_SET_OVERRIDE)) {
					if (!(sender instanceof SpoutPlayer)) {
						sender.sendMessage(ChatColor.RED + "You need to be a player to do that!");
						return false;
					}
					SpoutPlayer player = (SpoutPlayer) sender;
					
					player.setCape(mainClass.capeLocation.getAbsolutePath() + args[0] + ".png");
					player.sendMessage("Your cape has been set to " + args[0] + "!");
					return true;
				}
			}
			return true;
		}
		else if (args.length == 3) {
			
			return true;
		}
		else {
			sender.sendMessage(getDescription());
			return false;
		}
	}
}
