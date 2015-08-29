package com.himself12794.heroesmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;


public final class UtilMethods {

	private UtilMethods(){}
	
	public static boolean checkLiving(Entity entity) {
		
		return entity instanceof EntityLivingBase;
		
	}
	
}
