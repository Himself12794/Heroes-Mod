package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;

import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class PowerEffectsRegistration {
	
	public static void registerEffects() {
		
		PowerEffect.registerEffect(new RapidCellularRegeneration());
		PowerEffect.registerEffect(new Lift());
		PowerEffect.registerEffect(new Slam());
		PowerEffect.registerEffect(new PowerEffect("levitate"){
			
			@Override
			public boolean onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster, Power power) {
				entity.jumpMovementFactor = 0.0F;
				return true;
			}
		});
		
		PowerEffect.registerEffect(new Paralysis());
		PowerEffect.registerEffect(new EnhancedStrength());
		PowerEffect.registerEffect(new Flight());
		PowerEffect.registerEffect(new PhasingFx());
		PowerEffect.registerEffect(new Telekinesis());
		PowerEffect.registerEffect(new PowerEffect("break", true, EffectType.BENEFICIAL));
		PowerEffect.registerEffect(new PowerEffect("telekineticShield", true, EffectType.BENEFICIAL, true));
		PowerEffect.registerEffect(new PowerEffect("immortality", false, EffectType.TAG, true));
		
		
	}

}
