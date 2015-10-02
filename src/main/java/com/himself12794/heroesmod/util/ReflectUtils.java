package com.himself12794.heroesmod.util;

import java.lang.reflect.Field;

import com.himself12794.heroesmod.HeroesMod;

public final class ReflectUtils {

	private ReflectUtils() {}
	
	public static void setField(Class<?> clazz, Object obj, String name, Object value) {
		
		try {
			for (Field f : clazz.getFields()) {
				if (f.getName().equals(name)) {
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
	
}
