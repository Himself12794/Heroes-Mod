package com.himself12794.heroesmod.util;

import java.lang.reflect.Field;

import com.himself12794.heroesmod.HeroesMod;

public final class ReflectUtils {

	private ReflectUtils() {}
	
	public static void setField(Object obj, String name, String obfName, Object value) {
		
		try {
			for (Field f : obj.getClass().getFields()) {
				if (f.getName().equals(name) || f.getName().equals(obfName)) {
					f.setAccessible(true);
					f.set(obj, value);
					return;
				}
			}
		} catch (Exception e) {
			HeroesMod.logger().fatal("{} experienced a problem and cannot continue", Reference.MODID);
			throw new RuntimeException(e);
		}
		
	}
	
	public static Object getField(Object obj, String name, String obfName) {
		
		try {
			for (Field f : obj.getClass().getFields()) {
				if (f.getName().equals(name) || f.getName().equals(obfName)) {
					f.setAccessible(true);
					return f.get(obj);
				}
			}
		} catch (Exception e) {
			HeroesMod.logger().fatal("{} experienced a problem and cannot continue", Reference.MODID);
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
}
