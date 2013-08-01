package com.mctoybox.toybox.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {
	CAPE_NO_PERMISSION(ChatColor.RED, "You don't have permission to wear that cape!"),
	CAPE_NO_PERMISSION_OTHER(ChatColor.RED, "That person doesn't have permission to wear that cape!"),
	CAPE_NOT_FOUND(ChatColor.RED, "That cape could not be found!"),
	CAPE_NOT_FOUND_NOTE(ChatColor.RED, "Use " + ChatColor.DARK_RED + "/cape list" + ChatColor.RED + " to see capes you can wear"),
	
	CAPE_SET(ChatColor.GREEN, "Your cape has been changed!"),
	CAPE_SET_OTHER(ChatColor.GREEN, "Cape changed!"),
	
	CLASS_ALCHEMIST_BOOST_MESSAGE(ChatColor.GREEN, "Your knowledge of alchemy makes your potion last longer!"),
	CLASS_ALCHEMIST_ONLY_BREWING_STAND(ChatColor.RED, "Only an alchemist can use brewing stands!"),
	CLASS_ALCHEMIST_ONLY_TRANSMUTE(ChatColor.RED, "Only an alchemist can transmute things!"),
	
	CLASS_ARCHER_BOW_REPAIR(ChatColor.GREEN, "Your skill in archery prevents your bow being damaged!"),
	CLASS_NOT_FOUND(ChatColor.RED, "That class could not be found!"),
	
	INVALID_ARGUMENTS(ChatColor.RED, "Invalid arguments!"),
	
	NO_PERM_OVERRIDE_CAPE(ChatColor.RED, "You don't have permission to override cape permissions!"),
	NO_PERM_SET_OTHER_CAPE(ChatColor.RED, "You don't have permission to set another person's cape!"),
	NO_PERM_UNSPECIFIED(ChatColor.RED, "You don't have permission to do that!"),
	
	PLAYER_NOT_FOUND(ChatColor.RED, "Player could not be found!"),
	
	SPECIFY_PLAYER(ChatColor.RED, "You need to specify a player!"),
	
	UNKNOWN_COMMAND(ChatColor.RED, "Unknown command!");
	
	private ChatColor color;
	private String message;
	
	private Message(ChatColor color, String message) {
		this.color = color;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return color + message;
	}
	
	public static void sendMessage(CommandSender sender, Message message) {
		sender.sendMessage(message.toString());
	}
}
