package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.power.PowerInstant;

public class Break extends PowerEffectActivatorBuff {
	
	private final String name = "break";
	
	public Break() {
		setUnlocalizedName(name);
		setCoolDown(20 * 60);
	}


	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.breakFx;
	}

	@Override
	public int getEffectDuration() {
		return 20 * 60 * 3;
	}


	@Override
	public boolean isRemoveableByCaster(EntityLivingBase affected,
			EntityLivingBase caster, int timeRemaining) {
		
		boolean is = caster instanceof EntityPlayer ? ((EntityPlayer)caster).capabilities.isCreativeMode: false;
		
		return timeRemaining < 0 || is;
	}
	
}