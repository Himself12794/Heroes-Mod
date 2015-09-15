package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;
import com.himself12794.powersapi.storage.PowersEntity;

public class Slam extends PowerEffectActivatorInstant {
	
	public Slam() {
		super("slam", 140, 0, PowerEffects.slam, 50);
		setPower(10.0F);
		setCoolown(7 * 20);
		setUnlocalizedName("slam");
		
	}	
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier, int state) {
		
		if (target.typeOfHit == MovingObjectType.ENTITY) {

			if (target.entityHit instanceof EntityLivingBase) {
				PowersEntity.get((EntityLivingBase) target.entityHit).getPowerEffectsData()
						.removePowerEffectSparingly(PowerEffects.telekinesis);
			}
		}
		
		return true;
	}

}
