package com.mctoybox.toybox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.classes.ClassType;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class ClassCommandHandler extends CommandHandler {
	public ClassCommandHandler(MainClass mainClass) {
		super(mainClass);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String[] toPass = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			toPass[i - 1] = args[i];
		}
		
		if (args[0].equalsIgnoreCase("set")) {
			if (sender.hasPermission(Permissions.CLASS_SET_OWN)) {
				return SetClass(sender, toPass);
			}
			else {
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
			}
		}
		else if (args[0].equalsIgnoreCase("list")) {
			if (sender.hasPermission(Permissions.CLASS_LIST)) {
				return ListClasses(sender, toPass);
			}
			else {
				Message.sendMessage(sender, Message.NO_PERM_UNSPECIFIED);
			}
		}
		else {
			Message.sendMessage(sender, Message.UNKNOWN_COMMAND);
			return false;
		}
		return false;
	}
	
	private boolean SetClass(CommandSender sender, String[] args) {
		if (args.length == 0 || args.length > 2) {
			Message.sendMessage(sender, Message.INVALID_ARGUMENTS);
			return false;
		}
		if (args.length == 1 && !(sender instanceof SpoutPlayer)) {
			Message.sendMessage(sender, Message.SPECIFY_PLAYER);
			return false;
		}
		
		ClassType newClass = ClassType.getByName(args[0]);
		if (newClass == null) {
			Message.sendMessage(sender, Message.CLASS_NOT_FOUND);
			return false;
		}
		
		SpoutPlayer target = (SpoutPlayer) ((args.length == 1) ? sender : mainClass.getServer().getPlayer(args[1]));
		
		if (target == null) {
			Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
			return false;
		}
		
		try {
			mainClass.classList.getClassByType(newClass).assignPlayerToClass(target);
			if (sender instanceof SpoutPlayer && ((SpoutPlayer) sender).equals(target)) {
				
			}
			else {
				Message.sendMessage(sender, String.format(Message.CLASS_SET_TO_OTHER.toString(), target.getName(), newClass.getName()));
			}
		}
		catch (PlayerNotAllowedClassException e) {
			Message.sendMessage(sender, Message.CLASS_SET_TARGET_NOT_ALLOWED);
		}
		return true;
	}
	
	private boolean ListClasses(CommandSender sender, String[] args) {
		return false;
	}
}
