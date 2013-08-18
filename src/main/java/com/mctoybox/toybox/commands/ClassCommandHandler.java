package com.mctoybox.toybox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.classes.ClassType;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Message;

public class ClassCommandHandler extends CommandHandler {
	public ClassCommandHandler(MainClass mainClass) {
		super(mainClass);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName()) {
			case "class":
				Class(sender, command, args);
				break;
			case "setclass":
				SetClass(sender, command, args);
			case "listclass":
				ListClasses(sender, command, args);
			default:
				Message.sendMessage(sender, Message.UNKNOWN_COMMAND);
				return false;
		}
		return true;
	}
	
	private void Class(CommandSender sender, Command command, String[] args) {
		sender.sendMessage(command.getUsage());
	}
	
	private void SetClass(CommandSender sender, Command command, String[] args) {
		if (args.length == 0 || args.length > 2) {
			Message.sendMessage(sender, Message.INVALID_ARGUMENTS);
			sender.sendMessage(command.getUsage());
			return;
		}
		if (args.length == 1 && !(sender instanceof SpoutPlayer)) {
			Message.sendMessage(sender, Message.SPECIFY_PLAYER);
			sender.sendMessage(command.getUsage());
			return;
		}
		
		ClassType newClass = ClassType.getByName(args[0]);
		if (newClass == null) {
			Message.sendMessage(sender, Message.CLASS_NOT_FOUND);
			sender.sendMessage(command.getUsage());
			return;
		}
		
		SpoutPlayer target = (SpoutPlayer) ((args.length == 1) ? sender : mainClass.getServer().getPlayer(args[1]));
		
		if (target == null) {
			Message.sendMessage(sender, Message.PLAYER_NOT_FOUND);
			sender.sendMessage(command.getUsage());
			return;
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
	}
	
	private void ListClasses(CommandSender sender, Command command, String[] args) {
		
	}
}
