package com.mctoybox.toybox;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.classes.ClassType;
import com.mctoybox.toybox.util.Message;

public class Commands {
	private MainClass mainClass;
	
	public CapeCommands capeCommands;
	public ClassCommands classCommands;
	
	public Commands(MainClass mainClass) {
		this.mainClass = mainClass;
		
		this.capeCommands = new CapeCommands();
		this.classCommands = new ClassCommands();
		
	}
	
	public class CapeCommands {
		public void ListCapes(CommandSender sender, String[] args) {
			if (!(sender instanceof Player) && args.length == 0) {
				sender.sendMessage(ChatColor.RED + mainClass.getCommand("class").getUsage());
				return;
			}
			
			Player target = (sender instanceof Player) ? mainClass.getServer().getPlayer(args[0]) : (Player) sender;
			
			String[] returnList = { "Capes " + target.getName() + " can use:", "" };
			
			for (File f : mainClass.capeLocation.listFiles()) {
				if (f.isFile() && f.getName().split(".")[f.getName().split(".").length - 1].equalsIgnoreCase("png")) {
					String capeName = f.getName().substring(0, f.getName().lastIndexOf("."));
					
					if (mainClass.getConfig().getStringList("users." + target.getName().toLowerCase() + ".capes").contains(capeName)) {
						returnList[1] += capeName + ", ";
					}
				}
			}
			returnList[1] = returnList[1].substring(0, returnList[1].length() - 2);
			sender.sendMessage(returnList);
		}
		
		public void ListAllCapes(CommandSender sender, String[] args) {
			Player capeAccess;
			if (!(sender instanceof Player) && args.length == 0) {
				capeAccess = null;
			}
			else {
				capeAccess = (sender instanceof Player) ? mainClass.getServer().getPlayer(args[0]) : (Player) sender;
			}
			String[] returnList = { "Capes:", "" };
			
			for (File f : mainClass.capeLocation.listFiles()) {
				if (f.isFile() && f.getName().split(".")[f.getName().split(".").length - 1].equalsIgnoreCase("png")) {
					String capeName = f.getName().substring(0, f.getName().lastIndexOf("."));
					
					if (capeAccess != null) {
						if (mainClass.getConfig().getStringList("users." + capeAccess.getName().toLowerCase() + ".capes").contains(capeName)) {
							returnList[1] += ChatColor.GREEN + "";
						}
						else {
							returnList[1] += ChatColor.RED + "";
						}
					}
					returnList[1] += capeName;
					if (capeAccess != null) {
						returnList[1] += ChatColor.RESET;
					}
					returnList[1] += ", ";
				}
			}
			returnList[1] = returnList[1].substring(0, returnList[1].length() - 2);
			sender.sendMessage(returnList);
		}
		
		// TODO add override argument + permission
		// Permission: toybox.cape.set.override
		public void SetCape(CommandSender sender, String[] args) {
			
		}
		
		public void GrantCape(CommandSender sender, String[] args) {
			// TODO Auto-generated method stub
		}
		
		public void DenyCape(CommandSender sender, String[] args) {
			// TODO Auto-generated method stub
		}
	}
	
	public class ClassCommands {
		public void SetClass(CommandSender sender, String[] args) {
			// Command to get here is in the form: /class set <classname>
			// [target]
			// Example: /class set archer example_player
			// args = { "archer", "example_player" }
			if (args.length == 0 || args.length > 2) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments!");
				sender.sendMessage(ChatColor.RED + mainClass.getCommand("class").getUsage());
				return;
			}
			if (args.length == 2 && !(sender instanceof SpoutPlayer)) {
				Message.sendMessage(sender, Message.SPECIFY_PLAYER);
				sender.sendMessage(ChatColor.RED + mainClass.getCommand("class").getUsage());
				return;
			}
			
			ClassType newClass = ClassType.getByName(args[0]);
			if (newClass == null) {
				sender.sendMessage(ChatColor.RED + "That class could not be found!");
				return;
			}
			
			SpoutPlayer target = (SpoutPlayer) ((args.length == 1) ? sender : mainClass.getServer().getPlayer(args[1]));
			
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "The target could not be found!");
				return;
			}
			
			boolean isVowel = false;
			String firstLetter = newClass.getName().toLowerCase().substring(0, 1);
			if (firstLetter.equalsIgnoreCase("a") || firstLetter.equalsIgnoreCase("e") || firstLetter.equalsIgnoreCase("i") || firstLetter.equalsIgnoreCase("o") || firstLetter.equalsIgnoreCase("u"))
				isVowel = true;
			
			target.sendMessage("You have been made into a" + ((isVowel) ? "n " : " ") + newClass.getName());
			
			mainClass.playerClasses.setEitherClass(target.getName(), newClass);
			
			mainClass.getConfig().set(target.getName() + ".PrimaryClass", mainClass.playerClasses.getPrimaryClass(target).getName());
			mainClass.getConfig().set(target.getName() + ".SecondaryClass", mainClass.playerClasses.getSecondaryClass(target).getName());
			mainClass.saveConfig();
			
			mainClass.playerClasses.updateTitle(target);
		}
		
		public void ListClasses(CommandSender sender, String[] args) {
			// Command to get here is in the form: /class list
			String primary = "", secondary = "";
			// for (ClassBase cb : mainClass.ClassList) {
			// if (cb..getType().equals(Type.PRIMARY)) {
			// primary += cb.getName() + ", ";
			// }
			// else {
			// secondary += cb.getName() + ", ";
			// }
			// }
			sender.sendMessage("Class Listing:");
			sender.sendMessage("Primary: " + primary.substring(0, primary.length() - 2));
			sender.sendMessage("Secondary: " + secondary.substring(0, secondary.length() - 2));
		}
	}
}
