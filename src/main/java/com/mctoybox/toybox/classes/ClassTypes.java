package com.mctoybox.toybox.classes;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ClassTypes {
	OUTSIDER(0, "Outsider", Type.PRIMARY), ALCHEMIST(1, "Alchemist", Type.SECONDARY), ARCHER(2, "Archer", Type.PRIMARY), COOK(3, "Cook", Type.SECONDARY), ENCHANTER(4, "Enchanter", Type.SECONDARY), FARMER(
			5, "Farmer", Type.PRIMARY), LUMBERJACK(6, "Lumberjack", Type.PRIMARY), OREWORKER(7, "Oreworker", Type.PRIMARY), WARRIOR(8, "Warrior", Type.PRIMARY);
	
	private int number;
	private String name;
	private Type type;
	
	private final static Map<Integer, ClassTypes> BY_ID = Maps.newHashMap();
	private final static Map<String, ClassTypes> BY_NAME = Maps.newHashMap();
	
	private ClassTypes(int number, String name, Type type) {
		this.number = number;
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Type getType() {
		return type;
	}
	
	public static ClassTypes getPrimaryByID(int ID) {
		return BY_ID.get(ID);
	}
	
	public static ClassTypes getByName(String name) {
		return BY_NAME.get(name.toLowerCase());
	}
	
	public enum Type {
		PRIMARY, SECONDARY
	}
	
	static {
		for (ClassTypes cl : values()) {
			BY_ID.put(cl.number, cl);
			BY_NAME.put(cl.name.toLowerCase(), cl);
		}
	}
}
