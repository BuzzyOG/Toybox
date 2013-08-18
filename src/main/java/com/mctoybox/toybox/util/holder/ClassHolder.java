package com.mctoybox.toybox.util.holder;

import com.mctoybox.toybox.classes.ClassType;
import com.mctoybox.toybox.exceptions.NotPrimaryClassException;
import com.mctoybox.toybox.exceptions.NotSecondaryClassException;

public class ClassHolder {
	private ClassType primary;
	private ClassType secondary;
	
	public ClassHolder(ClassType primary, ClassType secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	
	public ClassHolder(ClassType primary) {
		this.primary = primary;
		this.secondary = null;
	}
	
	public ClassHolder() {
		this.primary = ClassType.OUTSIDER;
		this.secondary = null;
	}
	
	public ClassType getPrimary() {
		return primary;
	}
	
	public ClassType getSecondary() {
		return secondary;
	}
	
	public void setPrimary(ClassType newClass) throws NotPrimaryClassException {
		if (newClass.getType().equals(ClassType.Type.PRIMARY)) {
			this.primary = newClass;
		}
		else {
			throw new NotPrimaryClassException();
		}
	}
	
	public void setSecondary(ClassType newClass) throws NotSecondaryClassException {
		if (newClass.getType().equals(ClassType.Type.SECONDARY) || newClass == null) {
			this.secondary = newClass;
		}
		else {
			throw new NotSecondaryClassException();
		}
	}
}
