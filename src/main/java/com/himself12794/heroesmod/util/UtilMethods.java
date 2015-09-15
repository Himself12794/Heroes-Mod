package com.himself12794.heroesmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public final class UtilMethods {

	private UtilMethods(){}
	
	public static boolean checkLiving(Entity entity) {
		
		return entity instanceof EntityLivingBase;
		
	}
	
	public static float getDistanceToNextOccupiableSpace(World world, BlockPos pos) {
		
		float max = 20.0F;
		BlockPos currLoc = pos;
		float currLocF = 0.0F;
		boolean spaceOccupiable = false;
		
		do {
		
			if (world.isAirBlock(currLoc)) {
				if (world.isAirBlock(currLoc.up())) {
					spaceOccupiable = true;
				} else {
					currLocF = 0.0F;
					break;
				}
			}
			
			if (spaceOccupiable) {
				break;
			} else if (!spaceOccupiable) { 
				currLocF += 1.0F;
				currLoc = currLoc.up();
			}  
			
		} while (currLocF < max);
		
		return currLocF;
	}
	
	public static double getTotalVelocity(Entity entity) {
		double x = entity.motionX;
		double y = entity.motionY;
		double z = entity.motionZ;
		
		double totalVelocity = MathHelper.sqrt_double(x * x + z * z);
		return totalVelocity;
	}
	
}
