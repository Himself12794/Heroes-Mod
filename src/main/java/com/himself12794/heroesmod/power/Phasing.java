package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;

public class Phasing extends PowerEffectActivatorBuff {
	
	public Phasing() {
		setPower(0.0F);
		setCoolDown(100);
		setDuration(getEffectDuration());
		setUnlocalizedName("phasing");
	}	

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.phasing;
	}

	@Override
	public int getEffectDuration() {
		return 100;
	}

	@Override
	public boolean isRemoveableByCaster(EntityLivingBase affected,
			EntityLivingBase caster, int timeRemaining) {
		// TODO Auto-generated method stub
		return true;
	}

}
