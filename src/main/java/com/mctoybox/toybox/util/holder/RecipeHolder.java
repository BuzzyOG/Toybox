package com.mctoybox.toybox.util.holder;

import org.getspout.spoutapi.material.Material;

import com.mctoybox.toybox.classes.ClassType;

//DO I HAVE ENOUGH FUCKING CONSTRUCTORS JESUS FUCK
public class RecipeHolder {
	private MaterialHolder result;
	private MaterialHolder[] ingredients;
	private String pattern;
	private ClassType classRestriction;
	
	public RecipeHolder(Material result, Material... materials) {
		this(new MaterialHolder(result), "", null, materials);
	}
	
	public RecipeHolder(MaterialHolder result, Material... materials) {
		this(result, "", null, materials);
	}
	
	public RecipeHolder(Material result, MaterialHolder... materials) {
		this(new MaterialHolder(result), "", null, materials);
	}
	
	public RecipeHolder(MaterialHolder result, MaterialHolder... materials) {
		this(result, "", null, materials);
	}
	
	public RecipeHolder(Material result, ClassType classRestriction, Material... materials) {
		this(new MaterialHolder(result), "", classRestriction, materials);
	}
	
	public RecipeHolder(Material result, ClassType classRestriction, MaterialHolder... materials) {
		this(new MaterialHolder(result), "", classRestriction, materials);
	}
	
	public RecipeHolder(MaterialHolder result, ClassType classRestriction, Material... materials) {
		this(result, "", classRestriction, materials);
	}
	
	public RecipeHolder(MaterialHolder result, ClassType classRestriction, MaterialHolder... materials) {
		this(result, "", classRestriction, materials);
	}
	
	public RecipeHolder(Material result, String pattern, Material... materials) {
		this(new MaterialHolder(result), pattern, null, materials);
	}
	
	public RecipeHolder(Material result, String pattern, MaterialHolder... materials) {
		this(new MaterialHolder(result), pattern, null, materials);
	}
	
	public RecipeHolder(MaterialHolder result, String pattern, Material... materials) {
		this(result, pattern, null, materials);
	}
	
	public RecipeHolder(MaterialHolder result, String pattern, MaterialHolder... materials) {
		this(result, pattern, null, materials);
	}
	
	public RecipeHolder(Material result, String pattern, ClassType classRestriction, Material... materials) {
		this(new MaterialHolder(result), pattern, classRestriction, materials);
	}
	
	public RecipeHolder(Material result, String pattern, ClassType classRestriction, MaterialHolder... materials) {
		this(new MaterialHolder(result), pattern, classRestriction, materials);
	}
	
	public RecipeHolder(MaterialHolder result, String pattern, ClassType classRestriction, Material... materials) {
		this.result = result;
		this.pattern = pattern;
		this.classRestriction = classRestriction;
		
		this.ingredients = new MaterialHolder[materials.length];
		for (int i = 0; i < materials.length; i++) {
			this.ingredients[i] = new MaterialHolder(materials[i]);
		}
	}
	
	public RecipeHolder(MaterialHolder result, String pattern, ClassType classRestriction, MaterialHolder... materials) {
		this.result = result;
		this.ingredients = materials;
		this.pattern = pattern;
		this.classRestriction = classRestriction;
	}
	
	public boolean isIngredientInRecipe(MaterialHolder material) {
		for (MaterialHolder mat : ingredients) {
			if ((material.getMaterial().equals(mat.getMaterial())) && (material.getAmount() == mat.getAmount()) && (material.getDamage() == mat.getDamage())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isResultMatch(MaterialHolder resultToCheck) {
		return result.getAmount() == resultToCheck.getAmount() && result.getDamage() == resultToCheck.getDamage() && result.getMaterial().equals(resultToCheck.getAmount());
	}
	
	public MaterialHolder[] getMaterialsInPattern() {
		MaterialHolder[] toReturn = new MaterialHolder[9];
		for (int i = 0; i < toReturn.length; i++) {
			toReturn[i] = ingredients[Integer.parseInt(pattern, i)];
		}
		return toReturn;
	}
	
	public ClassType getClassRestriction() {
		return classRestriction;
	}
	
	public MaterialHolder[] getIngredients() {
		return ingredients;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public MaterialHolder getResult() {
		return result;
	}
}
