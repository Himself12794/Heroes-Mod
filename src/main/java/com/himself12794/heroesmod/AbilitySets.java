package com.himself12794.heroesmod;

import com.himself12794.heroesmod.ability.AbilitySet;

// TODO add Weather Manipulation
// TODO add Teleportation
public class AbilitySets {
	
	public static final AbilitySet pyrokinesis; 
	public static final AbilitySet telekinesis;
	public static final AbilitySet rapidCelluarRegneration;
	public static final AbilitySet spaceTimeManipulation;
	public static final AbilitySet emphaticMimicry;
	public static final AbilitySet enhancedSpeed;
	public static final AbilitySet enhancedStrength;

	static {
		
		if (HeroesMod.instance().isInitialized()) {
			
			pyrokinesis = AbilitySet.lookupAbilitySet("ability.pyrokinesis");
			telekinesis = AbilitySet.lookupAbilitySet("ability.telekinesis");
			rapidCelluarRegneration = AbilitySet.lookupAbilitySet("ability.rapidCelluarRegneration");
			spaceTimeManipulation = AbilitySet.lookupAbilitySet("ability.spaceTimeManipulation");
			emphaticMimicry = AbilitySet.lookupAbilitySet("ability.emphaticMimicry");
			enhancedSpeed = AbilitySet.lookupAbilitySet("ability.enhancedSpeed");
			enhancedStrength = AbilitySet.lookupAbilitySet("ability.enhancedStrength");
			HeroesMod.logger().debug("Ability set references instantiated");
			
		} else {
			throw new RuntimeException("Referenced ability sets before instantiation");
		}
		
	}
}
