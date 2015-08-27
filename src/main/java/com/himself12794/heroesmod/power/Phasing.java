package com.himself12794.heroesmod.power;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.IEffectActivator;
import com.himself12794.powersapi.power.PowerBuff;
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

}
