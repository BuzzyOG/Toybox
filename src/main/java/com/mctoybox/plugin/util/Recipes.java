package com.mctoybox.plugin.util;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.inventory.SpoutShapelessRecipe;
import org.getspout.spoutapi.material.Block;
import org.getspout.spoutapi.material.CustomItem;
import org.getspout.spoutapi.material.Item;
import org.getspout.spoutapi.material.Material;

public class Recipes {
	public static void NewShapedRecipe(CustomItem itemToCreate, String[] pattern, Material[] materials) {
		NewShapedRecipe(itemToCreate, 1, pattern, materials);
	}
	public static void NewShapedRecipe(CustomItem itemToCreate, int amount, String[] pattern, Material[] materials) {
		ItemStack result = new SpoutItemStack(itemToCreate, amount);
		SpoutShapedRecipe recipe = new SpoutShapedRecipe(result);
		recipe.shape(pattern);
		String uniqueChars = "", allChars = pattern[0] + pattern[1] + pattern[2];
		
		for(String s : allChars.split("")) {
			if(!uniqueChars.contains(s)) uniqueChars += s;
		}
		
		for(String s : uniqueChars.split("")){
			recipe.setIngredient(s.charAt(0), materials[Integer.parseInt(s)]);
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
	
	public static void NewShapedRecipe(Block blockToCreate, String[] pattern, Material[] materials) {
		NewShapedRecipe(blockToCreate, 1, pattern, materials);
	}
	public static void NewShapedRecipe(Block blockToCreate, int amount, String[] pattern, Material[] materials) {
		ItemStack result = new SpoutItemStack(blockToCreate, amount);
		SpoutShapedRecipe recipe = new SpoutShapedRecipe(result);
		recipe.shape(pattern);
		String uniqueChars = "", allChars = pattern[0] + pattern[1] + pattern[2];
		
		for(String s : allChars.split("")) {
			if(!uniqueChars.contains(s)) uniqueChars += s;
		}
		
		for(String s : uniqueChars.split("")){
			recipe.setIngredient(s.charAt(0), materials[Integer.parseInt(s)]);
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
	
	public static void NewShapelessRecipe(Item itemToCreate, Material[] materials, int[] matAmounts) {
		NewShapelessRecipe(itemToCreate, 1, materials, matAmounts);
	}
	public static void NewShapelessRecipe(Item itemToCreate, int amount, Material[] materials, int[] matAmounts) {
		ItemStack result = new SpoutItemStack(itemToCreate, amount);
		SpoutShapelessRecipe recipe = new SpoutShapelessRecipe(result);
		for(int i=0;i<materials.length;i++) {
			recipe.addIngredient(matAmounts[i], materials[i]);
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
	
	public static void NewShapelessRecipe(Block blockToCreate, Material[] materials, int[] matAmounts) {
		NewShapelessRecipe(blockToCreate, 1, materials, matAmounts);
	}
	public static void NewShapelessRecipe(Block blockToCreate, int amount, Material[] materials, int[] matAmounts) {
		ItemStack result = new SpoutItemStack(blockToCreate, amount);
		SpoutShapelessRecipe recipe = new SpoutShapelessRecipe(result);
		for(int i=0;i<materials.length;i++) {
			recipe.addIngredient(matAmounts[i], materials[i]);
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
	
}
