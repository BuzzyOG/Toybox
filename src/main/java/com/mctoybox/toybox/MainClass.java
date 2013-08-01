package com.mctoybox.toybox;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mctoybox.toybox.classes.Alchemist;
import com.mctoybox.toybox.classes.Archer;
import com.mctoybox.toybox.classes.ClassBase;
import com.mctoybox.toybox.classes.Cook;
import com.mctoybox.toybox.classes.Enchanter;
import com.mctoybox.toybox.classes.Farmer;
import com.mctoybox.toybox.classes.Lumberjack;
import com.mctoybox.toybox.classes.Oreworker;
import com.mctoybox.toybox.classes.Outsider;
import com.mctoybox.toybox.classes.Warrior;
import com.mctoybox.toybox.commands.CapeCommandHandler;
import com.mctoybox.toybox.commands.ClassCommandHandler;
import com.mctoybox.toybox.listeners.PlayerInteract;
import com.mctoybox.toybox.listeners.PlayerMove;
import com.mctoybox.toybox.listeners.SpoutCraftEnable;
import com.mctoybox.toybox.util.ClassHolder;
import com.mctoybox.toybox.util.ExtendedHashMap;

public class MainClass extends JavaPlugin {
	
	private PluginManager pm;
	private Commands cmds;
	
	public ExtendedHashMap<String, ClassHolder> playerClasses;
	
	private ClassBase alchemist, archer, cook, enchanter, farmer, lumberjack, oreworker, outsider, warrior;
	public ClassBase[] ClassList = new ClassBase[] { alchemist, archer, cook, enchanter, farmer, lumberjack, oreworker, outsider, warrior };
	
	private boolean debugMode;
	public File capeLocation;
	public List<String> capes;
	
	public void onEnable() {
		cmds = new Commands(this);
		saveDefaultConfig();
		
		getCommand("cape").setExecutor(new CapeCommandHandler(this));
		getCommand("class").setExecutor(new ClassCommandHandler(this));
		
		playerClasses = new ExtendedHashMap<String, ClassHolder>();
		
		debugMode = getConfig().getBoolean("config.debug", false);
		
		capeLocation = new File(getConfig().getString("config.capePath", "/path/to/cape/directory/"));
		capes = new ArrayList<String>();
		if (capeLocation.canRead())
			for (File f : capeLocation.listFiles()) {
				if (f.isFile()) {
					capes.add(f.getName().substring(0, f.getName().toLowerCase().lastIndexOf(".")));
				}
			}
		
		pm = getServer().getPluginManager();
		pm.registerEvents(new SpoutCraftEnable(this), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new PlayerMove(this), this);
		
		pm.registerEvents((alchemist = new Alchemist(this)), this);
		pm.registerEvents((archer = new Archer(this)), this);
		pm.registerEvents((cook = new Cook(this)), this);
		pm.registerEvents((enchanter = new Enchanter(this)), this);
		pm.registerEvents((farmer = new Farmer(this)), this);
		pm.registerEvents((lumberjack = new Lumberjack(this)), this);
		pm.registerEvents((oreworker = new Oreworker(this)), this);
		pm.registerEvents((outsider = new Outsider(this)), this);
		pm.registerEvents((warrior = new Warrior(this)), this);
	}
	
	public void onDisable() {
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String[] toPass = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			toPass[i - 1] = args[i];
		}
		
		if (command.getName().equalsIgnoreCase("cape")) {
			if (args[0].equalsIgnoreCase("set")) {
				if (sender.hasPermission(Permissions.CAPE_SET_OWN) || sender.hasPermission(Permissions.CAPE_SET_OTHER)) {
					for (int i = 1; i < args.length; i++) {
						toPass[i - 1] = args[i];
					}
					cmds.capeCommands.SetCape(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission(Permissions.CAPE_LIST)) {
					cmds.capeCommands.ListCapes(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else if (args[0].equalsIgnoreCase("listall")) {
				if (sender.hasPermission(Permissions.CAPE_LIST_ALL)) {
					cmds.capeCommands.ListAllCapes(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else if (args[0].equalsIgnoreCase("grant")) {
				if (sender.hasPermission(Permissions.CAPE_GRANT)) {
					cmds.capeCommands.GrantCape(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else if (args[0].equalsIgnoreCase("deny")) {
				if (sender.hasPermission(Permissions.CAPE_DENY)) {
					cmds.capeCommands.DenyCape(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else {
				sender.sendMessage("Unknown command!");
				sender.sendMessage(ChatColor.RED + getCommand("class").getUsage());
			}
		}
		else if (command.getName().equalsIgnoreCase("class")) {
			if (args[0].equalsIgnoreCase("set")) {
				if (sender.hasPermission("toybox.class.set")) {
					cmds.classCommands.SetClass(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("toybox.class.list")) {
					cmds.classCommands.ListClasses(sender, toPass);
				}
				else {
					sender.sendMessage(ChatColor.RED + "You don't have permission to do that!");
				}
			}
			else {
				sender.sendMessage("Unknown command!");
				sender.sendMessage(ChatColor.RED + getCommand("class").getUsage());
			}
		}
		return true;
	}
	
	public void debugOutput(Object message) {
		debugOutput(String.valueOf(message));
	}
	
	public void debugOutput(Boolean message) {
		debugOutput(String.valueOf(message));
	}
	
	public void debugOutput(String message) {
		if (debugMode)
			getLogger().log(Level.INFO, ChatColor.AQUA + "[Debug] " + message);
	}
}
