package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.network.SpawnParticlesClient;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;

public class Telekinesis extends PowerEffect {

	private static final String NAME = "telekinesis";
	private static final float DISTANCE_FROM_CASTER = 5.0F;

	public Telekinesis() {
		setUnlocalizedName(NAME);
		setType(EffectType.HIDDEN);
	}

	@Override
	public void onUpdate(EntityLivingBase entity, int timeLeft,
			EntityLivingBase caster, Power power) {

		if (entity != null && caster != null) {

			Vec3 vec = caster.getLookVec();
			double vX = vec.xCoord * DISTANCE_FROM_CASTER;
			double vY = vec.yCoord * DISTANCE_FROM_CASTER;
			double vZ = vec.zCoord * DISTANCE_FROM_CASTER;
			
			double x = caster.posX + vX;
			double y = caster.posY + caster.height + vY - entity.height / 2;
			double z = caster.posZ + vZ;
					
			entity.motionX = 0.0D;
			entity.motionY = 0.0D;
			entity.motionZ = 0.0D;
			entity.fallDistance = 0.0F;
			
			if (entity.worldObj.isAirBlock(entity.getPosition())) {
				entity.setPosition(x, y, z);	
			}

		}

	}

	/*@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster,
			Power power) {

		if (!entity.worldObj.isRemote) {
			HeroesMod.proxy.network.sendToAll(new SpawnParticlesClient(
					EnumParticleTypes.EXPLOSION_LARGE, entity.posX,
					entity.posY, entity.posZ));
		}

		double x = caster != null ? caster.getLookVec().xCoord : entity
				.getLookVec().xCoord;
		double y = caster != null ? caster.getLookVec().yCoord : entity
				.getLookVec().yCoord;
		double z = caster != null ? caster.getLookVec().zCoord : entity
				.getLookVec().zCoord;

		entity.motionX = 4.0D * x;
		entity.motionY = 4.0D * y;
		entity.motionZ = 4.0D * z;
	}*/

	@Override
	public boolean shouldApplyEffect(EntityLivingBase entity,
			EntityLivingBase caster,
			Power power) {
		
		return caster.getDistanceToEntity(entity) <= DISTANCE_FROM_CASTER
				&& this.getEffectTimeRemainingOn(entity) == 0;
	}

}
