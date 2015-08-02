package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;

import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.UsefulMethods;

public class Lift extends PowerEffect {
	
	private static final String NAME = "lift";
	
	public Lift() {
		setUnlocalizedName(NAME);
	}
	
	private static final double LIFT_HEIGHT = 5.0D;

	@Override
	public void onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster) {
		
		if (entity != null) {
			
			//System.out.println("Update triggered: " + entity.worldObj.isRemote + ", time left: " + timeLeft);
			
			if (!(entity instanceof EntityFlying) && entity.getHealth() > 0 ) {
				
				double groundDistance = UsefulMethods.distanceAboveGround(entity);
				
				if (groundDistance < LIFT_HEIGHT) entity.motionY = 1.0D;
				else entity.motionY = 0.0D;//entity.jumpMovementFactor = 0.0F;
			}
		}

	}

}
