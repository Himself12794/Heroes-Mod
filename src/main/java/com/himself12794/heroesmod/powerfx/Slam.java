package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.network.server.S01SpawnParticles;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;

public class Slam extends Lift {

	private static final String NAME = "slam";

	public Slam() {
		setUnlocalizedName(NAME);
		setType(EffectType.MALICIOUS);
	}

	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power) {
		
		if (!(entity instanceof EntityFlying) && entity != null) {
			entity.motionY = -4.0D;
			if (caster != entity) entity.fallDistance = 9.0F;
			if (!entity.worldObj.isRemote) {
				HeroesNetwork.client().spawnParticles(
						EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY,
						entity.posZ, 1.0F, 1, EnumRandomType.NORMAL, null);
			}
			if (power != Powers.NOVA) entity.playSound(Sounds.BIOTIC_EXPLOSION, 2.5F, 1.5F);
			
			double x = caster != null ? caster.getLookVec().xCoord : entity.getLookVec().xCoord;
			double y = caster != null ? caster.getLookVec().xCoord : entity.getLookVec().yCoord;
			double z = caster != null ? caster.getLookVec().zCoord : entity.getLookVec().zCoord;
			
			if (caster != entity) {
				entity.motionX = 4.0D * x;
				entity.motionZ = 4.0D * z;
			}
			
		}

	}

}
