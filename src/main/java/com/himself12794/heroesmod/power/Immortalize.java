package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.util.DataWrapper;

public class Immortalize extends PowerEffectActivatorBuff {
	
	public Immortalize() {
		setUnlocalizedName("immortalize");
	}

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.rapidCellularRegeneration;
	}

	@Override
	public int getEffectDuration() {
		return -1;
	}

}
