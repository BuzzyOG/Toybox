package com.mctoybox.toybox.util;

import org.getspout.spoutapi.material.Material;

public class MaterialHolder {
	private Material material;
	private int amount;
	private short damage;
	
	public MaterialHolder(String material) {
		this(ItemChecker.LookupItem("Toybox", material));
	}
	
	public MaterialHolder(String plugin, String material) {
		this(ItemChecker.LookupItem(plugin, material));
	}
	
	public MaterialHolder(Material material) {
		this(material, 1, (short) 0);
	}
	
	public MaterialHolder(Material material, int amount) {
		this(material, amount, (short) 0);
	}
	
	public MaterialHolder(Material material, short damage) {
		this(material, 1, damage);
	}
	
	public MaterialHolder(Material material, int amount, short damage) {
		this.amount = amount;
		this.material = material;
		this.damage = damage;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public short getDamage() {
		return damage;
	}
}
