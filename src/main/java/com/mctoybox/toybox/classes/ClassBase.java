package com.mctoybox.toybox.classes;

import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;

public abstract class ClassBase implements Listener {
	protected MainClass mainClass;
	protected String className;
	protected ClassTypes classRef;
	protected Permission permRequired;
	
	public ClassBase(MainClass mainClass, String ClassName, Permission permRequired) {
		this.mainClass = mainClass;
		this.className = ClassName;
		this.classRef = ClassTypes.getByName(className);
		this.permRequired = permRequired;
	}
	
	public String getName() {
		return className;
	}
	
	public ClassTypes getClassRef() {
		return classRef;
	}
	
	public Permission getPermRequired() {
		return permRequired;
	}
	
	public abstract void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException;
}
