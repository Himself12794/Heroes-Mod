package com.himself12794.heroesmod;

import net.minecraft.entity.EntityLivingBase;

import com.himself12794.heroesmod.powerfx.EnhancedStrength;
import com.himself12794.heroesmod.powerfx.Flight;
import com.himself12794.heroesmod.powerfx.Lift;
import com.himself12794.heroesmod.powerfx.Paralysis;
import com.himself12794.heroesmod.powerfx.PhasingFx;
import com.himself12794.heroesmod.powerfx.RapidCellularRegeneration;
import com.himself12794.heroesmod.powerfx.Slam;
import com.himself12794.powersapi.power.PowerEffect;

public class PowerEffects {
	
	public static final RapidCellularRegeneration rapidCellularRegeneration;
	public static final Lift lift;
	public static final Slam slam;
	public static final PowerEffect levitate;
	public static final Paralysis paralysis;
	public static final EnhancedStrength enhancedStrength;
	public static final PowerEffect flight;
	public static final PowerEffect phasing;
	
	static {
		
		if (HeroesMod.instance.isInitialized()) {
		
			rapidCellularRegeneration = (RapidCellularRegeneration) PowerEffect.getPowerEffect("rapidCellularRegeneration");
			lift = (Lift) PowerEffect.getPowerEffect("lift");
			slam = (Slam) PowerEffect.getPowerEffect("slam");
			levitate = PowerEffect.getPowerEffect("levitate");		
			paralysis = (Paralysis) PowerEffect.getPowerEffect("paralysis");
			enhancedStrength = (EnhancedStrength) PowerEffect.getPowerEffect("enhancedStrength");
			flight = PowerEffect.getPowerEffect("flight");
			phasing = PowerEffect.getPowerEffect("phasing");
			
		} else {
			throw new RuntimeException("References accessed before registration");
		}
		
	}

}
