package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;

import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class PowerEffects {
	
	public static void registerEffects() {
		
		PowerEffect.registerEffect(new RapidCellularRegeneration());
		PowerEffect.registerEffect(new Lift());
		PowerEffect.registerEffect(new Slam());
		PowerEffect.registerEffect(new PowerEffect(){
			
			private static final String NAME = "levitation";
			
			{
				setUnlocalizedName(NAME);
			}
			
			@Override
			public void onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster, Power power) {
				entity.jumpMovementFactor = 0.0F;
				
			}
		});
		
		PowerEffect.registerEffect(new Paralysis());
		PowerEffect.registerEffect(new EnhancedStrength());
		PowerEffect.registerEffect(new Flight());
		PowerEffect.registerEffect(new PhasingFx());
		
	}

}
