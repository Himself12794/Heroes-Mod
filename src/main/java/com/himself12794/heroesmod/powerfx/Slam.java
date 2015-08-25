package com.himself12794.heroesmod.powerfx;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.network.SpawnParticlesClient;

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

		// System.out.println("is client " + !entity.worldObj.isRemote);
		if (!(entity instanceof EntityFlying) && entity != null) {
			entity.motionY = -4.0D;
			entity.fallDistance = 9.0F;
			//entity.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
			//		entity.posX, entity.posY, entity.posZ, 0, 0, 0);
			if (!entity.worldObj.isRemote) {
				HeroesMod.proxy.network.sendToAll(new SpawnParticlesClient(
						EnumParticleTypes.EXPLOSION_LARGE, entity.posX,
						entity.posY, entity.posZ));
			}
			entity.playSound("random.explode", 1, 1);
			
		}

	}

}
