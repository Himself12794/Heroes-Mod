package com.himself12794.heroesmod.powerfx;

import com.himself12794.powersapi.powerfx.PowerEffect;

public class PowerEffects {
	
	public static PowerEffect rapidCellularRegeneration;
	public static PowerEffect lift;
	public static PowerEffect slam;
	public static PowerEffect levitate;
	public static PowerEffect paralysis;
	
	public static void registerEffects() {
		
		rapidCellularRegeneration = PowerEffect.registerEffect(new RapidCellularRegeneration());
		lift = PowerEffect.registerEffect(new Lift());
		slam = PowerEffect.registerEffect(new Slam());
		levitate = PowerEffect.registerEffect(new Levitate());
		paralysis = PowerEffect.registerEffect(new Paralysis());
		
	}

}
