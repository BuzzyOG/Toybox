package com.mctoybox.plugin.util;

import com.mctoybox.plugin.classes.ClassTypes;
import com.mctoybox.plugin.exceptions.NotPrimaryClassException;
import com.mctoybox.plugin.exceptions.NotSecondaryClassException;

public class ClassHolder {
	private ClassTypes primary;
	private ClassTypes secondary;
	
	public ClassHolder(ClassTypes primary, ClassTypes secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}
	public ClassHolder(ClassTypes primary) {
		this.primary = primary;
		this.secondary = null;
	}
	public ClassHolder() {
		this.primary = ClassTypes.OUTSIDER;
		this.secondary = null;
	}
	
	public ClassTypes getPrimary() { return primary; }
	public ClassTypes getSecondary() { return secondary; }
	
	public void setPrimary(ClassTypes newClass) throws NotPrimaryClassException {
		if(newClass.getType().equals(ClassTypes.Type.PRIMARY)) {
			this.primary = newClass;
		} else {
			throw new NotPrimaryClassException();
		}
	}
	
	public void setSecondary(ClassTypes newClass) throws NotSecondaryClassException {
		if(newClass.getType().equals(ClassTypes.Type.SECONDARY) || newClass == null) {
			this.secondary = newClass;
		} else {
			throw new NotSecondaryClassException();
		}
	}
}
