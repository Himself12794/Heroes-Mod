package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;

import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.UsefulMethods;

public class Lift extends PowerEffect {
	
	private final String NAME = "lift";
	
	private final double LIFT_HEIGHT = 5.0D;
	
	public Lift() {
		setUnlocalizedName(NAME);
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster, Power power) {
		
		if (entity != null) {
			
			if (!(entity instanceof EntityFlying) ) {
				
				double groundDistance = UsefulMethods.distanceAboveGround(entity);
				
				if (groundDistance < LIFT_HEIGHT) entity.motionY = 1.0D;
				else entity.motionY = 0.0D;
				
				if (power == Powers.nova && caster == entity) entity.motionY = 0.5D;
			}
		}
		
		return true;

	}

}
