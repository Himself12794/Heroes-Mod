package com.himself12794.heroesmod.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;

import com.google.common.collect.Maps;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class AbilitySet {
	
	private final List<Power> activePowers = new ArrayList<Power>();
	private final List<PowerEffect> passivePowerEffects = new ArrayList<PowerEffect>();
	private final List<Integer> potionEffects = new ArrayList<Integer>();
	private final String unlocalizedName;
	private String description = ""; 
	
	AbilitySet(String name) {
		this.unlocalizedName = name;
	}
	
	AbilitySet addActivePower(Power power) {
		
		if (!activePowers.contains(power)) activePowers.add(power);
		
		return this;
		
	}
	
	AbilitySet addPassivePower(PowerEffect pfx) {
		
		if (!passivePowerEffects.contains(pfx)) passivePowerEffects.add(pfx);
		return this;
		
	}
	
	AbilitySet addPassivePower(Potion pfx) {
		
		if (!potionEffects.contains(pfx.id)) potionEffects.add(pfx.id);
		return this;
		
	}
	
	AbilitySet setDescription(String desc) {
		
		this.description = desc;
		return this;
		
	}
	
	public String getDescription() { return description; }
	
	public boolean hasPower(Power power) { return activePowers.contains(power); }
	
	public boolean hasPower(PowerEffect pfx) { return passivePowerEffects.contains(pfx); }
	
	public boolean hasPower(Potion pfx) { return passivePowerEffects.contains(pfx); }
	
	/**
	 * Returns a copy of the active powers attributed to this Ability Set.
	 * 
	 * @return
	 */
	public List<Power> getActivePowers() {
		return new ArrayList<Power>(activePowers);
	}
	
	/**
	 * Returns a copy of the passive powers attributed to this Ability Set.
	 * 
	 * @return
	 */
	public List<PowerEffect> getPassivePowers() {
		return new ArrayList<PowerEffect>(passivePowerEffects);
	}
	
	/**
	 * Returns a copy of the passive powers (Potion effects) attributed to this Ability Set.
	 * 
	 * @return
	 */
	public List<Integer> getPassivePowersPotion() {
		return new ArrayList<Integer>(potionEffects);
	}
	
	public String getUnlocalizedName() {
		return "ability." + unlocalizedName;
	}
	
	public String getDisplayName() {
		return ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name")).trim();
	}
	
	/*========================= Ability Set Registration =========================*/
	
	public final static Map<String, AbilitySet> abilitySets = Maps.newHashMap();
	public final static Map<Integer, String> abilitySetIds = Maps.newHashMap(); 
	private static int abilitySetsCount = 0;
	
	public static void registerAbilitySets() {
		
		registerAbilitySet((new AbilitySet("pyrokinesis"))
				.addActivePower(Powers.flames)
				.addActivePower(Powers.incinerate)
				.addPassivePower(Potion.fireResistance)
				.setDescription("The ability to manipulate and resist fire.")
				);
		
		registerAbilitySet((new AbilitySet("telekinesis"))
				.addActivePower(Powers.push)
				.addActivePower(Powers.slam)
				.addActivePower(Powers.telekinesis)
				.setDescription("The ability to move things with the mind.")
				);
		
		registerAbilitySet((new AbilitySet("rapidCellularRegeneration"))
				.addPassivePower(PowerEffects.rapidCellularRegeneration)
				.setDescription("The ability to recover near-instantly from any wound, fatal or non-fatal.")
				);
		
		registerAbilitySet(new AbilitySet("spaceTimeManipulation")
				.setDescription("The ability to teleport and slow/stop time.")
				);
		
		registerAbilitySet(new AbilitySet("flight")
				.addPassivePower(PowerEffects.flight)
				.setDescription("The ability of flight.")
				);
		
		HeroesMod.logger.info("Registered " + abilitySetsCount + " ability set(s)");
	}
	
	
	private static AbilitySet registerAbilitySet(AbilitySet abs) {
		
		String name = abs.getUnlocalizedName();
		//System.out.println("trying to register new ability set");
		if (!abilitySetExists(abs)) {
			
			abilitySets.put(name, abs);
			abilitySetIds.put(abilitySetsCount, name);
			//HeroesMod.logger.info("Registered ability set " + name);
			
			++abilitySetsCount;
			return abs;
			
		} else {
			
			HeroesMod.logger.error("Could not register ability set " + abs + " under name \"" + name + "\", name has already been registered for " + lookupAbilitySet(name));
			return null;
			
		}
	}
	
	public static AbilitySet lookupAbilitySet(String name) {
		return abilitySets.get(name);
	}

	public static boolean abilitySetExists(AbilitySet abs) {
		return abilitySets.containsValue(abs);
	}
	
	public static int getAbilitySetCount() {
		return abilitySetsCount;
	}
	
}
