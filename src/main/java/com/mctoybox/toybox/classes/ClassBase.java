package com.mctoybox.toybox.classes;

import org.bukkit.event.Listener;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.exceptions.PlayerNotAllowedClassException;
import com.mctoybox.toybox.util.Permissions;

public abstract class ClassBase implements Listener {
	protected MainClass mainClass;
	protected String className;
	protected ClassType classRef;
	protected Permissions permRequired;
	
	protected ClassBase(MainClass mainClass, String ClassName, Permissions permRequired) {
		this.mainClass = mainClass;
		this.className = ClassName;
		this.classRef = ClassType.getByName(className);
		this.permRequired = permRequired;
		
		mainClass.getServer().getPluginManager().registerEvents(this, mainClass);
	}
	
	public abstract void assignPlayerToClass(SpoutPlayer player) throws PlayerNotAllowedClassException;
}
