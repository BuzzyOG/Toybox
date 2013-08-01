package com.mctoybox.toybox.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.mctoybox.toybox.MainClass;

public class ClassCommandHandler extends CommandHandler implements CommandExecutor {
	public ClassCommandHandler(MainClass mainClass) {
		super(mainClass, "class");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
}
