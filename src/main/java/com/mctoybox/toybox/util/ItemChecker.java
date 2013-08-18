package com.mctoybox.toybox.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.CustomBlock;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.material.MaterialData;

import com.mctoybox.toybox.MainClass;
import com.mctoybox.toybox.util.holder.MaterialHolder;
import com.mctoybox.toybox.util.holder.RecipeHolder;

public class ItemChecker {
	public static List<RecipeHolder> recipeList;
	
	static {
		recipeList = new ArrayList<RecipeHolder>();
	}
	
	public static Material LookupItem(Plugin plugin, String itemName) {
		return LookupItem(plugin.getName(), itemName);
	}
	
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
	
	public static RecipeHolder GetToyboxRecipe(ItemStack[] items) {
		return GetToyboxRecipe(items, false);
	}
	
	public static RecipeHolder GetToyboxRecipe(ItemStack[] items, boolean hasPattern) {
		// items should be a crafting grid, items[0] being the result
		int itemAmount = 0;
		for (ItemStack item : items) {
			if (!item.getType().equals(org.bukkit.Material.AIR)) {
				itemAmount += 1;
			}
		}
		for (RecipeHolder recipe : recipeList) {
			MaterialHolder result = new MaterialHolder(MaterialData.getMaterial(items[0].getTypeId()), items[0].getAmount());
			if (!recipe.isResultMatch(result)) {
				return null;
			}
			
			for (int i = 1; i < items.length; i++) {
				if (hasPattern) {
					if (!recipe.getMaterialsInPattern()[i - 1].getMaterial().equals(MaterialData.getMaterial(items[0].getTypeId()))) {
						return null;
					}
				}
				else {
					if (itemAmount != recipe.getIngredients().length) {
						return null;
					}
					Material itemToCheck = MaterialData.getMaterial(items[i].getTypeId());
					if (!recipe.isIngredientInRecipe(new MaterialHolder(itemToCheck, items[i].getAmount()))) {
						return null;
					}
				}
			}
			return recipe;
		}
		// for (RecipeHolder recipe : recipeList) {
		// if (recipe.getPattern().equals(pattern)) {
		// MaterialHolder result = new
		// MaterialHolder(MaterialData.getMaterial(items[0].getTypeId()),
		// items[0].getAmount());
		// if (!recipe.isResultMatch(result)) {
		// return null;
		// }
		// for (ItemStack item : items) {
		// MaterialHolder toCheck = new
		// MaterialHolder(MaterialData.getMaterial(item.getTypeId()),
		// item.getAmount());
		// if (!recipe.isIngredientInRecipe(toCheck)) {
		// return null;
		// }
		// }
		// }
		// }
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
