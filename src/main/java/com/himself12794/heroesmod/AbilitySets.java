package com.himself12794.heroesmod;

import com.himself12794.heroesmod.ability.AbilitySet;


public class AbilitySets {
	
	public static final AbilitySet pyrokinesis; 
	public static final AbilitySet telekinesis;
	public static final AbilitySet rapidCelluarRegneration;
	public static final AbilitySet spaceTimeManipulation;

	static {
		
		if (HeroesMod.instance.isInitialized()) {
			
			pyrokinesis = AbilitySet.lookupAbilitySet("pyrokinesis");
			telekinesis = AbilitySet.lookupAbilitySet("telekinesis");
			rapidCelluarRegneration = AbilitySet.lookupAbilitySet("rapidCelluarRegneration");
			spaceTimeManipulation = AbilitySet.lookupAbilitySet("spaceTimeManipulation");
			HeroesMod.logger.debug("Ability set references instantiated");
			
		} else {
			throw new RuntimeException("Referenced ability sets before instantiation");
		}
		
	}
}
