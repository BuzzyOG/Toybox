package com.mctoybox.toybox.util;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Permissions extends Permission {
	public static final Permission CAPE_DENY = new Permissions("toybox.cape.deny", "Allows players to deny access to capes to other people", PermissionDefault.OP);
	public static final Permission CAPE_GRANT = new Permissions("toybox.cape.grant", "Allows players to give access to capes to other people", PermissionDefault.OP);
	public static final Permission CAPE_LIST = new Permissions("toybox.cape.list", "Allows viewing capes a player can wear");
	public static final Permission CAPE_LIST_ALL = new Permissions("toybox.cape.list.all", "Allows viewing all capes", PermissionDefault.OP, CAPE_LIST);
	public static final Permission CAPE_SET_OWN = new Permissions("toybox.cape.set.own", "Allows a player to set their cape");
	public static final Permission CAPE_SET_OTHER = new Permissions("toybox.cape.set.other", "Allows a player to set other people's cape", PermissionDefault.OP);
	public static final Permission CAPE_SET_OVERRIDE = new Permissions("toybox.cape.set.override", "Allows players to ignore restrictions on capes when setting", PermissionDefault.OP);
	
	public static final Permission CLASS_LIST = new Permissions("toybox.class.list", "Allows a player to list classes a player can become");
	public static final Permission CLASS_LIST_ALL = new Permissions("toybox.class.list.all", "Allows a player to list all classes", PermissionDefault.OP, CLASS_LIST);
	public static final Permission CLASS_SET_OWN = new Permissions("toybox.class.set.own", "Allows a player to set their own class", PermissionDefault.OP);
	public static final Permission CLASS_SET_OTHER = new Permissions("toybox.class.set.other", "Allows a player to set other people's classes", PermissionDefault.OP, CLASS_SET_OWN);
	public static final Permission CLASS_SET_OVERRIDE = new Permissions("toybox.class.set.override", "Allows a player to override class restrictions", PermissionDefault.OP, CLASS_SET_OWN,
			CLASS_SET_OTHER);
	
	public static final Permission CLASSES_ALCHEMIST = new Permissions("toybox.classes.alchemist", "Allows a player to become an alchemist");
	public static final Permission CLASSES_ARCHER = new Permissions("toybox.classes.archer", "Allows a player to become an archer");
	public static final Permission CLASSES_COOK = new Permissions("toybox.classes.cook", "Allows a player to become a cook");
	public static final Permission CLASSES_ENCHANTER = new Permissions("toybox.classes.enchanter", "Allows a player to become an enchanter");
	public static final Permission CLASSES_FARMER = new Permissions("toybox.classes.farmer", "Allows a player to become a farmer");
	public static final Permission CLASSES_LUMBERJACK = new Permissions("toybox.classes.lumberjack", "Allows a player to become a lumberjack");
	public static final Permission CLASSES_OREWORKER = new Permissions("toybox.classes.oreworker", "Allows a player to become an oreworker");
	public static final Permission CLASSES_OUTSIDER = new Permissions("toybox.classes.outsider", "Allows a player to become an outsider");
	public static final Permission CLASSES_WARRIOR = new Permissions("toybox.classes.warrior", "Allows a player to become a warrior");
	
	public static final Permission CAPE_ALL = new Permissions("toybox.cape.*", "Allows access to all cape commands", PermissionDefault.OP, CAPE_DENY, CAPE_GRANT, CAPE_LIST, CAPE_LIST_ALL,
			CAPE_SET_OWN, CAPE_SET_OTHER, CAPE_SET_OVERRIDE);
	public static final Permission CLASS_ALL = new Permissions("toybox.class.*", "Allows access to all class commands", PermissionDefault.OP, CLASS_LIST, CLASS_SET_OWN, CLASS_SET_OTHER,
			CLASS_SET_OVERRIDE);
	public static final Permission CLASSES_ALL = new Permissions("toybox.classes.*", "Allows access to all classes", CLASSES_ALCHEMIST, CLASSES_ARCHER, CLASSES_COOK, CLASSES_ENCHANTER,
			CLASSES_FARMER, CLASSES_LUMBERJACK, CLASSES_OREWORKER, CLASSES_OUTSIDER, CLASSES_WARRIOR);
	
	public static final Permission ALL = new Permissions("toybox.*", "Allows access to all Toybox commands", PermissionDefault.OP, CAPE_ALL, CLASS_ALL, CLASSES_ALL);
	
	private Permissions(String permission, String description) {
		super(permission, description, PermissionDefault.TRUE);
	}
	
	private Permissions(String permission, String description, PermissionDefault def) {
		super(permission, description, def);
	}
	
	private Permissions(String permission, String description, Permission... children) {
		super(permission, description, PermissionDefault.TRUE);
		for (Permission s : children) {
			this.getChildren().put(s.getName(), true);
		}
		this.recalculatePermissibles();
	}
	
	private Permissions(String permission, String description, PermissionDefault defaultAccess, Permission... children) {
		super(permission, description, defaultAccess);
		for (Permission s : children) {
			this.getChildren().put(s.getName(), true);
		}
		this.recalculatePermissibles();
	}
	
	private Permissions(String permission, String description, PermissionDefault defaultAccess, String... children) {
		super(permission, description, defaultAccess);
		for (String s : children) {
			this.getChildren().put(s, true);
		}
		this.recalculatePermissibles();
	}
}
