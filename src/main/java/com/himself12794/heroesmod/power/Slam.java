package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;
import com.himself12794.powersapi.util.DataWrapper;

public class Slam extends PowerEffectActivatorInstant {
	
	public Slam() {
		
		setPower(10.0F);
		setCoolDown(7 * 20);
		setUnlocalizedName("slam");
		
	}	
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		if (target.typeOfHit == MovingObjectType.ENTITY) {

			if (target.entityHit instanceof EntityLivingBase) {
				DataWrapper.get((EntityLivingBase) target.entityHit).powerEffectsData
						.removePowerEffectSparingly(PowerEffects.telekinesis);
			}
		}
		
		return true;
	}

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.slam;
	}

	@Override
	public int getEffectDuration() {
		return 50;
	}

	@Override
	public boolean isRemoveableByCaster(EntityLivingBase affected,
			EntityLivingBase caster, int timeRemaining) {
		// TODO Auto-generated method stub
		return true;
	}

}
