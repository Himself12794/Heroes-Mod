package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;
import com.himself12794.powersapi.storage.PowersWrapper;

public class Telekinesis extends PowerEffectActivatorInstant {

	public Telekinesis() {
		super("telekinesis", 160, 200, PowerEffects.telekinesis, 200);
		setPower(0.0F);
		setDuration(15 * 20);
		setRange(100);

	}

	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		PowersWrapper.get(caster).getPowerEffectsData().addPowerEffect(PowerEffects.telekineticShield, -1, caster, this);
		return true;
	}
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier, int state) {
		
		if (target.entityHit != null) {

			if (target.entityHit instanceof EntityLivingBase)
				PowersWrapper.get((EntityLivingBase) target.entityHit).getPowerEffectsData()
						.addPowerEffect(PowerEffects.paralysis, getDuration(),
								caster, this);

			if (target.entityHit.getDistanceToEntity(caster) >= 5.0F) {

				Vec3 look = caster.getLookVec();

				target.entityHit.motionX = -look.xCoord * 2.0F;
				target.entityHit.motionY = -look.yCoord * 2.0F;
				target.entityHit.motionZ = -look.zCoord * 2.0F;
			} else {
				target.entityHit.motionX = 0.0D;
				target.entityHit.motionY = 0.0D;
				target.entityHit.motionZ = 0.0D;
			}

			return true;
		}

		return false;

	}

}
