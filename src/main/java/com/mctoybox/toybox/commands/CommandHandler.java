package com.mctoybox.toybox.commands;

import org.bukkit.command.CommandExecutor;

import com.mctoybox.toybox.MainClass;

public abstract class CommandHandler implements CommandExecutor {
	protected MainClass mainClass;
	
	public CommandHandler(MainClass mainClass) {
		this.mainClass = mainClass;
	}
}
