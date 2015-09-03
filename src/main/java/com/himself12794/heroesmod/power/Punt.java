package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.DataWrapper;
import com.himself12794.powersapi.util.UsefulMethods;

public class Punt extends PowerInstant {

	private final float puntVelocity = 3.5F;

	public Punt() {

		setPower(0.0F);
		setMaxConcentrationTime(20 *5);
		setCoolDown(60);
		setUnlocalizedName("punt");
		setRange(50);

	}

	@Override
	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		if (target.typeOfHit == MovingObjectType.ENTITY) {

			if (target.entityHit instanceof EntityLivingBase) {
				DataWrapper.get((EntityLivingBase) target.entityHit).getPowerEffectsData()
						.removePowerEffectSparingly(PowerEffects.telekinesis);
			}

			double x = caster.getLookVec().xCoord;
			double y = caster.getLookVec().yCoord;
			double z = caster.getLookVec().zCoord;

			x *= puntVelocity;
			y *= puntVelocity;
			z *= puntVelocity;

			target.entityHit.motionX = x;
			target.entityHit.motionY = y;
			target.entityHit.motionZ = z;

			return true;

		}

		return false;

	}

}
