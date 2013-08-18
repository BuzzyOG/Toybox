package com.mctoybox.toybox.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum Permissions {
	CAPE_DENY("toybox.cape.deny", "Allows players to deny access to capes to other people", PermissionDefault.OP),
	CAPE_GRANT("toybox.cape.grant", "Allows players to give access to capes to other people", PermissionDefault.OP),
	CAPE_LIST("toybox.cape.list", "Allows viewing capes a player can wear"),
	CAPE_LIST_ALL("toybox.cape.list.all", "Allows viewing all capes", PermissionDefault.OP, CAPE_LIST),
	CAPE_SET_OWN("toybox.cape.set.own", "Allows a player to set their cape"),
	CAPE_SET_OTHER("toybox.cape.set.other", "Allows a player to set other people's cape", PermissionDefault.OP),
	CAPE_SET_OVERRIDE("toybox.cape.set.override", "Allows players to ignore restrictions on capes when setting", PermissionDefault.OP),
	
	CLASS_LIST("toybox.class.list", "Allows a player to list classes a player can become"),
	CLASS_LIST_ALL("toybox.class.list.all", "Allows a player to list all classes", PermissionDefault.OP, CLASS_LIST),
	CLASS_SET_OWN("toybox.class.set.own", "Allows a player to set their own class", PermissionDefault.OP),
	CLASS_SET_OTHER("toybox.class.set.other", "Allows a player to set other people's classes", PermissionDefault.OP, CLASS_SET_OWN),
	CLASS_SET_OVERRIDE("toybox.class.set.override", "Allows a player to override class restrictions", PermissionDefault.OP, CLASS_SET_OWN, CLASS_SET_OTHER),
	
	CLASSES_ALCHEMIST("toybox.classes.alchemist", "Allows a player to become an alchemist"),
	CLASSES_ARCHER("toybox.classes.archer", "Allows a player to become an archer"),
	CLASSES_COOK("toybox.classes.cook", "Allows a player to become a cook"),
	CLASSES_ENCHANTER("toybox.classes.enchanter", "Allows a player to become an enchanter"),
	CLASSES_FARMER("toybox.classes.farmer", "Allows a player to become a farmer"),
	CLASSES_LUMBERJACK("toybox.classes.lumberjack", "Allows a player to become a lumberjack"),
	CLASSES_OREWORKER("toybox.classes.oreworker", "Allows a player to become an oreworker"),
	CLASSES_OUTSIDER("toybox.classes.outsider", "Allows a player to become an outsider"),
	CLASSES_WARRIOR("toybox.classes.warrior", "Allows a player to become a warrior"),
	
	CAPE_ALL("toybox.cape.*", "Allows access to all cape commands", PermissionDefault.OP, CAPE_DENY, CAPE_GRANT, CAPE_LIST, CAPE_LIST_ALL, CAPE_SET_OWN, CAPE_SET_OTHER, CAPE_SET_OVERRIDE),
	CLASS_ALL("toybox.class.*", "Allows access to all class commands", PermissionDefault.OP, CLASS_LIST, CLASS_SET_OWN, CLASS_SET_OTHER, CLASS_SET_OVERRIDE),
	CLASSES_ALL(
			"toybox.classes.*",
			"Allows access to all classes",
			CLASSES_ALCHEMIST,
			CLASSES_ARCHER,
			CLASSES_COOK,
			CLASSES_ENCHANTER,
			CLASSES_FARMER,
			CLASSES_LUMBERJACK,
			CLASSES_OREWORKER,
			CLASSES_OUTSIDER,
			CLASSES_WARRIOR),
	
	ALL("toybox.*", "Allows access to all Toybox commands", PermissionDefault.OP, CAPE_ALL, CLASS_ALL, CLASSES_ALL);
	
	private List<Permission> permList = new ArrayList<Permission>();
	private String name, description;
	private PermissionDefault permDefault;
	private Permissions[] children;
	
	private Permissions(String permission, String description) {
		this(permission, description, PermissionDefault.TRUE);
	}
	
	private Permissions(String permission, String description, PermissionDefault def) {
		this(permission, description, def, new Permissions[] {});
	}
	
	private Permissions(String permission, String description, Permissions... children) {
		this(permission, description, PermissionDefault.TRUE, children);
	}
	
	private Permissions(String permission, String description, PermissionDefault defaultAccess, Permissions... children) {
		this.name = permission;
		this.description = description;
		this.permDefault = defaultAccess;
		this.children = new Permissions[children.length];
		
		Permission temp = new Permission(permission, description, defaultAccess);
		if (children.length != 0) {
			for (Permissions s : children) {
				temp.getChildren().put(s.getName(), true);
			}
			temp.recalculatePermissibles();
			permList.add(temp);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public PermissionDefault getPermissionDefault() {
		return permDefault;
	}
	
	public Permissions[] getChildren() {
		return children;
	}
	
	public Permission getPermissionByName(String name) {
		for (Permission s : permList) {
			if (s.getName().equalsIgnoreCase(name))
				return s;
		}
		return null;
	}
}
