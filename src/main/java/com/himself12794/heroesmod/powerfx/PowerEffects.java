package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;

import com.himself12794.powersapi.power.PowerEffect;

public class PowerEffects {
	
	public static PowerEffect rapidCellularRegeneration;
	public static PowerEffect lift;
	public static PowerEffect slam;
	public static PowerEffect levitate;
	public static PowerEffect paralysis;
	public static PowerEffect enhancedStrength;
	public static PowerEffect flight;
	public static PowerEffect phasing;
	
	public static void registerEffects() {
		
		rapidCellularRegeneration = PowerEffect.registerEffect(new RapidCellularRegeneration());
		lift = PowerEffect.registerEffect(new Lift());
		slam = PowerEffect.registerEffect(new Slam());
		levitate = PowerEffect.registerEffect(new PowerEffect(){

			@Override
			public void onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster) {
				entity.jumpMovementFactor = 0.0F;
				
			}
		});
		
		paralysis = PowerEffect.registerEffect(new Paralysis());
		enhancedStrength = PowerEffect.registerEffect( new EnhancedStrength());
		flight = PowerEffect.registerEffect(new Flight());
		phasing = PowerEffect.registerEffect(new PhasingFx());
		
	}

}
