package com.himself12794.heroesmod.util;

public enum EnumRandomType {
	GAUSSIAN(0), NORMAL(1);
	
	private final int id;
	
	EnumRandomType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static EnumRandomType fromId(int i) {
		
		for (EnumRandomType type : values()) {
			if (i == type.id) return type;
		}
		
		return null;
		
	}
}
