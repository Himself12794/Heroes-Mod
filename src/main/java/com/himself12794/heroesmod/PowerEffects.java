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
	
	public static final RapidCellularRegeneration rapidCellularRegeneration;
	public static final Lift lift;
	public static final Slam slam;
	public static final Paralysis paralysis;
	public static final EnhancedStrength enhancedStrength;
	//public static final PhasingFx phasing;
	public static final Telekinesis telekinesis;
	public static final EmphaticMimicry emphaticMimicry;
	public static final PowerEffect levitate;
	public static final PowerEffect flight;
	public static final PowerEffect breakFx;
	public static final PowerEffect telekineticShield;
	public static final PowerEffect immortality;
	public static final PowerEffect speedBoost;
	public static final PowerEffect karma;
	
	static {
		
		if (HeroesMod.instance().isInitialized()) {
			
			rapidCellularRegeneration = (RapidCellularRegeneration) PowerEffect.getPowerEffect("rapidCellularRegeneration");
			lift = (Lift) PowerEffect.getPowerEffect("lift");
			slam = (Slam) PowerEffect.getPowerEffect("slam");
			levitate = PowerEffect.getPowerEffect("levitate");		
			paralysis = (Paralysis) PowerEffect.getPowerEffect("paralysis");
			enhancedStrength = (EnhancedStrength) PowerEffect.getPowerEffect("enhancedStrength");
			flight = PowerEffect.getPowerEffect("flight");
			//phasing = (PhasingFx) PowerEffect.getPowerEffect("phasing");
			telekinesis = (Telekinesis) PowerEffect.getPowerEffect("telekinesis");
			breakFx = PowerEffect.getPowerEffect("break");
			telekineticShield = PowerEffect.getPowerEffect("telekineticShield");
			immortality = PowerEffect.getPowerEffect("immortality");
			emphaticMimicry = (EmphaticMimicry) PowerEffect.getPowerEffect("emphaticMimicry");
			speedBoost = PowerEffect.getPowerEffect("speedBoost");
			karma = PowerEffect.getPowerEffect("karma");
			
		} else {
			throw new RuntimeException("References accessed before registration");
		}
		
	}

}
