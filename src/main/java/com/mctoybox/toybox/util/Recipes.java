package com.mctoybox.toybox.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.inventory.SpoutShapelessRecipe;
import org.getspout.spoutapi.material.Material;

import com.mctoybox.toybox.exceptions.NotEnoughArgumentsException;

public class Recipes {
	// Shaped: Item, Damage, Amount, Pattern, Materials
	// Shapeless: Item, damage, amount, {material, amount}
	public static void NewShapedRecipe(MaterialHolder toCreate, String pattern, Material... materials) {
		if (2 > materials.length || materials.length > 9) {
			throw new IllegalArgumentException();
		}
		if (toCreate == null) {
			throw new NullArgumentException("toCreate");
		}
		ItemStack result = new SpoutItemStack(toCreate.getMaterial(), toCreate.getAmount());
		if (toCreate.getDamage() != 0)
			result.setDurability(toCreate.getDamage());
		
		SpoutShapedRecipe recipe = new SpoutShapedRecipe(result);
		
		recipe.shape(pattern.substring(0, 3), pattern.substring(3, 6), pattern.substring(6, 9));
		List<Character> uniqueChars = new ArrayList<Character>();
		for (Character s : pattern.toCharArray()) {
			if (!uniqueChars.contains(s))
				uniqueChars.add(s);
		}
		for (int i = 0; i < uniqueChars.size(); i++) {
			Character c = uniqueChars.get(i);
			recipe.setIngredient(c, materials[i]);
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
	
	public static void NewShapelessRecipe(MaterialHolder toMake, Material... materials) {
		MaterialHolder[] toPass = new MaterialHolder[materials.length + 1];
		toPass[0] = toMake;
		for (int i = 0; i < materials.length; i++) {
			toPass[i + 1] = new MaterialHolder(materials[i]);
		}
		NewShapelessRecipe(toPass);
	}
	
	/**
	 * The first material is the result of the recipe, while the others are
	 * ingredients
	 * 
	 * @param materials
	 * @throws NotEnoughArgumentsException
	 */
	public static void NewShapelessRecipe(MaterialHolder... materials) throws IllegalArgumentException {
		if (2 > materials.length || materials.length > 9) {
			throw new IllegalArgumentException();
		}
		ItemStack result = new SpoutItemStack(materials[0].getMaterial(), materials[0].getAmount());
		if (materials[0].getDamage() != 0)
			result.setDurability(materials[0].getDamage());
		
		SpoutShapelessRecipe recipe = new SpoutShapelessRecipe(result);
		for (int i = 1; i < materials.length; i++) {
			recipe.addIngredient(materials[i].getAmount(), materials[i].getMaterial());
		}
		SpoutManager.getMaterialManager().registerSpoutRecipe(recipe);
	}
}
