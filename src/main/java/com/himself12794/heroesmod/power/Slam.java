package com.himself12794.heroesmod.power;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;

public class Slam extends PowerEffectActivatorInstant {
	
	public Slam() {
		
		setPower(10.0F);
		setCoolDown(7 * 20);
		setUnlocalizedName("slam");
		
	}

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.slam;
	}

	@Override
	public int getEffectDuration() {
		return 50;
	}

}
