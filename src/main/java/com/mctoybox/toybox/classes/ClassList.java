package com.mctoybox.toybox.classes;

import java.util.ArrayList;
import java.util.List;

import com.mctoybox.toybox.MainClass;

public class ClassList {
	public List<ClassBase> classList = new ArrayList<ClassBase>();
	
	public ClassList(MainClass mainClass) {
		classList.add(new Alchemist(mainClass));
		classList.add(new Archer(mainClass));
		classList.add(new Cook(mainClass));
		classList.add(new Enchanter(mainClass));
		classList.add(new Farmer(mainClass));
		classList.add(new Lumberjack(mainClass));
		classList.add(new Oreworker(mainClass));
		classList.add(new Outsider(mainClass));
		classList.add(new Warrior(mainClass));
		
		for (ClassBase base : classList) {
			mainClass.getServer().getPluginManager().registerEvents(base, mainClass);
		}
	}
	
	public ClassBase getClassByName(String className) {
		for (ClassBase base : classList) {
			if (base.className.equalsIgnoreCase(className)) {
				return base;
			}
		}
		return null;
	}
	
	public ClassBase getClassByType(ClassType type) {
		for (ClassBase base : classList) {
			if (base.classRef.equals(type)) {
				return base;
			}
		}
		return null;
	}
}
