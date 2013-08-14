package com.mctoybox.toybox.util;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

import com.mctoybox.toybox.MainClass;

public class ItemChecker {
	public static Material LookupItem(String pluginName, String itemName) {
		for (Material material : MaterialData.getMaterials()) {
			if (material instanceof CustomItem) {
				CustomItem item = (CustomItem) material;
				if (item.getFullName().equalsIgnoreCase(pluginName + "." + itemName)) {
					return item;
				}
			}
			else if (material instanceof CustomBlock) {
				CustomBlock block = (CustomBlock) material;
				if (block.getFullName().equalsIgnoreCase(pluginName + "." + itemName)) {
					return block;
				}
				
			}
			else {
				if (material.getName().equalsIgnoreCase(itemName)) {
					return material;
				}
			}
		}
		return null;
	}
	
	public static boolean CraftingArrayHas(MainClass mainClass, ItemStack[] items, org.bukkit.Material itemToFind) {
		return CraftingArrayHas(mainClass, items, new ItemStack(itemToFind));
	}
	
	public static boolean CraftingArrayHas(MainClass mainClass, ItemStack[] items, Material itemToFind) {
		return CraftingArrayHas(mainClass, items, new SpoutItemStack(itemToFind));
	}
	
	public static boolean CraftingArrayHas(MainClass mainClass, ItemStack[] items, ItemStack itemToFind) {
		for (int i = 1; i < items.length; i++) {
			if (items[i] == null)
				continue;
			if (items[i].getType().name().equalsIgnoreCase(itemToFind.getType().name()))
				return true;
		}
		return false;
	}
	
	public static boolean CraftingArrayHasExactAmount(MainClass mainClass, ItemStack[] items, org.bukkit.Material itemToFind, int amountToFind) {
		return CraftingArrayHasExactAmount(mainClass, items, new ItemStack(itemToFind), amountToFind);
	}
	
	public static boolean CraftingArrayHasExactAmount(MainClass mainClass, ItemStack[] items, Material itemToFind, int amountToFind) {
		return CraftingArrayHasExactAmount(mainClass, items, new SpoutItemStack(itemToFind), amountToFind);
	}
	
	public static boolean CraftingArrayHasExactAmount(MainClass mainClass, ItemStack[] items, ItemStack itemToFind, int amountToFind) {
		int amountFound = 0;
		for (int i = 1; i < items.length; i++) {
			if (items[i] == null)
				continue;
			if (items[i].getType().name().equalsIgnoreCase(itemToFind.getType().name()))
				amountFound++;
		}
		return amountFound == amountToFind;
	}
	
	public static boolean CraftingArrayHasMinAmount(MainClass mainClass, ItemStack[] items, org.bukkit.Material itemToFind, int amountToFind) {
		return CraftingArrayHasMinAmount(mainClass, items, new ItemStack(itemToFind), amountToFind);
	}
	
	public static boolean CraftingArrayHasMinAmount(MainClass mainClass, ItemStack[] items, Material itemToFind, int amountToFind) {
		return CraftingArrayHasMinAmount(mainClass, items, new SpoutItemStack(itemToFind), amountToFind);
	}
	
	public static boolean CraftingArrayHasMinAmount(MainClass mainClass, ItemStack[] items, ItemStack itemToFind, int amountToFind) {
		int amountFound = 0;
		for (int i = 1; i < items.length; i++) {
			if (items[i] == null)
				continue;
			if (items[i].getType().name().equalsIgnoreCase(itemToFind.getType().name()))
				amountFound++;
			if (amountFound >= amountToFind)
				return true;
		}
		return false;
	}
	
}
