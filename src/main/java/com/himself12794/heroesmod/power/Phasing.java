package com.himself12794.heroesmod.power;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.IEffectActivator;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.power.PowerEffect;

public class Phasing extends PowerBuff implements IEffectActivator {
	
	public Phasing() {
		setPower(0.0F);
		setCoolDown(100);
		setDuration(-1);
		setUnlocalizedName("phasing");
	}	

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.phasing;
	}

}
