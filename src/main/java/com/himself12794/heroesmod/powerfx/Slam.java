package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;

public class Slam extends Lift {
	
	private static final String NAME = "slam";
	
	public Slam() {
		setUnlocalizedName(NAME);
	}

	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster) {
		
		//System.out.println("is client " + !entity.worldObj.isRemote);
		if (!(entity instanceof EntityFlying) && entity != null) {
			entity.motionY = -4.0D;
			//entity.fall(10.0F, 1.0F);
			entity.fallDistance = 9.0F;
			entity.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, 
					entity.posX, 
					entity.posY, 
					entity.posZ, 
					0, 0, 0);
			entity.playSound("random.explode", 1, 1);
			//entity.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX, entity.posY + (double)(entity.height / 2.0F), entity.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		

	}

}
