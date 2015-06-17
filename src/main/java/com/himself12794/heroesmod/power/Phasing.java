package com.himself12794.heroesmod.power;

import com.himself12794.heroesmod.powerfx.PowerEffects;
import com.himself12794.powersapi.power.IEffectActivator;
import com.himself12794.powersapi.power.PowerEffect;

public class Phasing implements IEffectActivator {

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.phasing;
	}

}
