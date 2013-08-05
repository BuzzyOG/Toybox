package com.mctoybox.toybox.classes;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Message;
import com.mctoybox.toybox.util.Permissions;

public class Enchanter extends ClassBase {
	
	public Enchanter(MainClass mainClass) {
		super(mainClass, "Enchanter", Permissions.CLASSES_ENCHANTER);
	}
	
	@Override
	public void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException {
		if (!player.hasPermission(permRequired)) {
			throw new PlayerNotAllowedClassException();
		}
		
		player.sendMessage(String.format(Message.CLASS_SET_TO.toString(), className));
		
		if (classRef.getType().equals(ClassType.Type.PRIMARY)) {
			mainClass.playerClasses.setPrimaryClass(player.getName(), classRef);
			mainClass.getConfig().set(player.getName() + ".PrimaryClass", classRef.getName());
		}
		else {
			mainClass.playerClasses.setSecondaryClass(player.getName(), classRef);
			mainClass.getConfig().set(player.getName() + ".SecondaryClass", classRef.getName());
		}
		mainClass.playerClasses.updateTitle(player);
		mainClass.saveConfig();
	}
}
