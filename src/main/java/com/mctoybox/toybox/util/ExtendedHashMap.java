package com.mctoybox.toybox.util;

import java.util.HashMap;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.classes.ClassTypes;

public class ExtendedHashMap<T, V> extends HashMap<String, ClassHolder> {
	private static final long serialVersionUID = 1L;
	
	public ClassTypes getPrimaryClass(String player) {
		return get(player).getPrimary();
	}
	
	public ClassTypes getPrimaryClass(SpoutPlayer player) {
		return get(player.getName()).getPrimary();
	}
	
	public ClassTypes getSecondaryClass(String player) {
		return get(player).getSecondary();
	}
	
	public ClassTypes getSecondaryClass(SpoutPlayer player) {
		return get(player.getName()).getSecondary();
	}
	
	public ClassHolder getAllClasses(String player) {
		return get(player);
	}
	
	public void setPrimaryClass(String player, ClassTypes primaryClass) {
		setAllClasses(player, primaryClass, getSecondaryClass(player));
	}
	
	public void setSecondaryClass(String player, ClassTypes secondaryClass) {
		setAllClasses(player, getPrimaryClass(player), secondaryClass);
	}
	
	public void setEitherClass(String player, ClassTypes eitherClass) {
		if (eitherClass.getType().equals(ClassTypes.Type.PRIMARY))
			setPrimaryClass(player, eitherClass);
		else
			setSecondaryClass(player, eitherClass);
	}
	
	public void setAllClasses(String player, ClassTypes primaryClass, ClassTypes secondaryClass) {
		put(player, new ClassHolder(primaryClass, secondaryClass));
	}
	
	public void updateTitle(SpoutPlayer player) {
		player.setTitle(getTitle(player));
	}
	
	public String getTitle(SpoutPlayer player) {
		return player.getDisplayName() + ((getPrimaryClass(player) != null) ? "\n" + getPrimaryClass(player).getName() : "")
				+ ((getSecondaryClass(player) != null) ? "\n" + getSecondaryClass(player).getName() : "");
	}
}
