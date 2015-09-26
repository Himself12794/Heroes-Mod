package com.himself12794.heroesmod;

import com.himself12794.heroesmod.powerfx.EmphaticMimicry;
import com.himself12794.heroesmod.powerfx.EnhancedStrength;
import com.himself12794.heroesmod.powerfx.Lift;
import com.himself12794.heroesmod.powerfx.Paralysis;
import com.himself12794.heroesmod.powerfx.RapidCellularRegeneration;
import com.himself12794.heroesmod.powerfx.Slam;
import com.himself12794.heroesmod.powerfx.Telekinesis;
import com.himself12794.powersapi.power.PowerEffect;

public class PowerEffects {
	
	public static final PowerEffect rapidCellularRegeneration;
	public static final PowerEffect lift;
	public static final PowerEffect slam;
	public static final PowerEffect paralysis;
	public static final PowerEffect enhancedStrength;
	public static final PowerEffect telekinesis;
	public static final PowerEffect emphaticMimicry;
	public static final PowerEffect levitate;
	public static final PowerEffect flight;
	public static final PowerEffect breakFx;
	public static final PowerEffect telekineticShield;
	public static final PowerEffect immortality;
	public static final PowerEffect speedBoost;
	public static final PowerEffect karma;
	
	static {
		
		if (HeroesMod.getMod().isInitialized()) {
			
			rapidCellularRegeneration = PowerEffect.getPowerEffect("rapidCellularRegeneration");
			lift = PowerEffect.getPowerEffect("lift");
			slam = PowerEffect.getPowerEffect("slam");
			levitate = PowerEffect.getPowerEffect("levitate");		
			paralysis = PowerEffect.getPowerEffect("paralysis");
			enhancedStrength = PowerEffect.getPowerEffect("enhancedStrength");
			flight = PowerEffect.getPowerEffect("flight");
			telekinesis = PowerEffect.getPowerEffect("telekinesis");
			breakFx = PowerEffect.getPowerEffect("break");
			telekineticShield = PowerEffect.getPowerEffect("telekineticShield");
			immortality = PowerEffect.getPowerEffect("immortality");
			emphaticMimicry = PowerEffect.getPowerEffect("emphaticMimicry");
			speedBoost = PowerEffect.getPowerEffect("speedBoost");
			karma = PowerEffect.getPowerEffect("karma");
			
		} else {
			throw new RuntimeException("References accessed before registration");
		}
		
	}

}
