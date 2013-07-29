package com.mctoybox.plugin.commands;

import org.bukkit.command.Command;

import com.mctoybox.plugin.MainClass;

public class CommandHandler {
	protected MainClass mainClass;
	
	private String usage;
	private String description;
	
	public CommandHandler(MainClass mainClass, String commandName) {
		this.mainClass = mainClass;
		Command cmd = mainClass.getCommand(commandName);
		this.usage = cmd.getUsage();
		this.description = cmd.getDescription();
	}
	
	protected String getUsage() {
		return usage;
	}
	
	public String getDescription() {
		return description;
	}
}
