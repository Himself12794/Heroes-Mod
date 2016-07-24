package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.util.UsefulMethods;

public class Slam extends Lift {

	private static final String NAME = "slam";

	public Slam() {
		setUnlocalizedName(NAME);
		setType(EffectType.MALICIOUS);
		setRequiresCaster();
	}

	@SuppressWarnings("unused")
	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power) {
		
		super.onRemoval(entity, caster, power);
		
		if (!(entity instanceof EntityFlying) && entity != null) {
			
			entity.motionY = -10.0D;
			
			if (caster != entity) {
				entity.fallDistance = 15.0F;
			} 
			
			if (!entity.worldObj.isRemote) {
				HeroesNetwork.client().spawnParticles(
						EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY,
						entity.posZ, 1.0F, 1, EnumRandomType.NORMAL, null);
			}
			
			if (power != Powers.nova) entity.playSound(Sounds.BIOTIC_EXPLOSION, 2.5F, 1.5F);
			
			double x = caster != null ? caster.getLookVec().xCoord : entity.getLookVec().xCoord;
			double y = caster != null ? caster.getLookVec().yCoord : entity.getLookVec().yCoord;
			double z = caster != null ? caster.getLookVec().zCoord : entity.getLookVec().zCoord;
			
			if (caster != null && caster != entity) {
				
				Vec3 target = UsefulMethods.getMouseOverExtendedUniversal(caster, 100.0F).hitVec;
				Vec3 launchVector = target.subtract(entity.getPositionVector());
				launchVector = launchVector.normalize();
				
				entity.motionX = 4.0D * launchVector.xCoord;
				entity.motionY = 4.0D * launchVector.yCoord;
				entity.motionZ = 4.0D * launchVector.zCoord;
				
			} else {
				
				entity.motionX = 4.0D * x;
				entity.motionZ = 4.0D * z;
				
			}
			
		}

	}

}
