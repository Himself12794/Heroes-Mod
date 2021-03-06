package com.himself12794.heroesmod.ability;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.util.RandomUtils;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

/**
 * The ability set. Contains the powers this ability should allow, and the effects
 * that should be constant on the entity.
 * 
 * @author Himself12794
 *
 */
public class AbilitySet implements RandomUtils.IWeightedItem {

	private float weight; 
	private final Map<Power, Integer> activePowers = Maps.newHashMap();
	private final Set<PowerEffect> passivePowerEffects = Sets.newHashSet();
	private final Set<Integer> potionEffects = Sets.newHashSet();
	private final String unlocalizedName;
	private String description = "";

	public AbilitySet(String name) {
		this.unlocalizedName = name;
	}

	protected AbilitySet addActivePower( Power power ) {
		return addActivePower( power, 1 );
	}

	protected AbilitySet addActivePower( Power power, int requiredLevel ) {

		if (!activePowers.containsKey(power))
			activePowers.put(power, requiredLevel);

		return this;

	}

	protected AbilitySet addPassivePower(PowerEffect pfx) {

		if (!passivePowerEffects.contains(pfx))
			passivePowerEffects.add(pfx);
		return this;

	}

	protected AbilitySet addPassivePower(Potion pfx) {

		if (!potionEffects.contains(pfx.id))
			potionEffects.add(pfx.id);
		return this;

	}

	protected AbilitySet setDescription(String desc) {

		this.description = desc;
		return this;

	}

	public String getDescription() {
		return description;
	}

	public boolean hasPower(Power power) {
		return activePowers.containsKey(power);
	}

	public boolean hasPower(PowerEffect pfx) {
		return passivePowerEffects.contains(pfx);
	}

	public boolean hasPower(Potion pfx) {
		return passivePowerEffects.contains(pfx);
	}

	/**
	 * Returns a copy of the active powers attributed to this Ability Set.
	 * 
	 * @return
	 */
	public Collection<Power> getActivePowers() {
		return activePowers.keySet();
	}

	/**
	 * Returns the passive powers attributed to this Ability Set.
	 * 
	 * @return
	 */
	public Collection<PowerEffect> getPassivePowers() {
		return passivePowerEffects;
	}

	/**
	 * Returns the passive powers (Potion effects) attributed to this
	 * Ability Set.
	 * 
	 * @return
	 */
	public Collection<Integer> getPassivePowersPotion() {
		return potionEffects;
	}
	
	public String getLoggerFriendlyName() {
		char[] chars = unlocalizedName.toCharArray();
		chars[0] = String.valueOf(chars[0]).toUpperCase().toCharArray()[0];
		
		return String.valueOf(chars);
	}

	public String getUnlocalizedName() {
		return "ability." + unlocalizedName;
	}

	public String getDisplayName() {
		return ("" + StatCollector.translateToLocal(getUnlocalizedName()
				+ ".name")).trim();
	}
	
	public int getTotalLevels(EntityLivingBase entity) {
		
		PowersEntity pwrs = PowersEntity.get(entity);
		int sum = 0;
		
		for ( Power power : activePowers.keySet() ) {
			PowerProfile profile = pwrs.getPowerProfile(power);
			sum += profile.level;
		}
		
		return sum;
	}
	
	public int getRequiredLevel(Power power) {
		if (activePowers.containsKey(power)) 
			return activePowers.get(power);
		
		return 0;
	}

	/**
	 * Determines how common an ability is. The higher the number, the more
	 * common the ability. Range is 0.0F - 20.0F
	 * 
	 * @param weight
	 * @return
	 */
	public AbilitySet setWeight(float weight) {
		this.weight = weight > 20.0F ? 20.0F : (weight < 0.0F ? 0.0F : weight);
		return this;
	}

	@Override
	public float getWeight() {
		return weight;
	}

	/*
	 * ========================= Ability Set Registration =========================
	 */

	public final static Map<String, AbilitySet> abilitySets = Maps.newHashMap();
	public final static Map<Integer, String> abilitySetIds = Maps.newHashMap();
	private static int abilitySetsCount = 0;

	public static void registerAbilitySets() {

		registerAbilitySet((new AbilitySet("pyrokinesis")).setWeight(2.0F)
				.addActivePower(Powers.flames)
				.addActivePower(Powers.incinerate)
				.addPassivePower(Potion.fireResistance)
				.setDescription("The ability to manipulate and resist heat."));

		registerAbilitySet((new AbilitySet("telekinesis")).setWeight(5.0F)
				.addActivePower(Powers.punt)
				.addActivePower(Powers.telekinesis)
				.addActivePower(Powers.slam, 6)
				.addPassivePower(PowerEffects.telekineticShield)
				.setDescription("The ability to move things with the mind."));

		registerAbilitySet((new AbilitySet("rapidCellularRegeneration"))
				.setWeight(0.5F)
				.addPassivePower(PowerEffects.rapidCellularRegeneration)
				.setDescription("The ability to recover near-instantly from any wound, fatal or non-fatal."));

		registerAbilitySet(new AbilitySet("teleportation")
				.setWeight(0.1F)
				.addActivePower(Powers.teleportation)
				.addActivePower(Powers.blinkJump)
				.setDescription("The ability to teleport."));

		registerAbilitySet((new AbilitySet("materialManipulation"))
				.setWeight(10.0F)
				.addActivePower(Powers.blockMemory)
				.addActivePower(Powers.enderAccess)
				.addPassivePower(PowerEffects.breakFx)
				.setDescription("The ability to manipulate solid matter and shift it between dimensions."));

		registerAbilitySet((new AbilitySet("flight"))
				.setWeight(4.0F)
				.addActivePower(Powers.launch)
				.addActivePower(Powers.nova)
				.addActivePower(Powers.charge, 6)
				.addPassivePower(PowerEffects.flight)
				.setDescription("The ability of flight."));
		
		registerAbilitySet((new AbilitySet("emphaticMimicry"))
				.setWeight(0.0F)
				.addPassivePower(PowerEffects.emphaticMimicry)
				.setDescription("Harmless at first, you absorb abilities of others simply by being near them."));
		
		registerAbilitySet((new AbilitySet("enhancedSpeed"))
				.setWeight(3.0F)
				.addActivePower(Powers.speedBoost)
				.addActivePower(Powers.charge)
				.addActivePower(Powers.specializedPunch, 5)
				.addPassivePower(Potion.digSpeed)
				.setDescription("The ability to move very fast.")
				);
		
		registerAbilitySet((new AbilitySet("enhancedStrength"))
				.setWeight(3.0F)
				.addActivePower(Powers.specializedPunch)
				.addActivePower(Powers.nova, 4)
				.addPassivePower(Potion.damageBoost)
				.setDescription("The ability of super strength")
				);
	}

	protected static AbilitySet registerAbilitySet(AbilitySet abs) {

		String name = abs.getUnlocalizedName();

		if (!abilitySetExists(abs)) {

			abilitySets.put(name, abs);
			abilitySetIds.put(++abilitySetsCount, name);
			HeroesMod.logger().info("Registered ability set " + abs.getLoggerFriendlyName());

			return abs;

		} else {

			HeroesMod.logger().error("Could not register ability set " + abs
					+ " under name \"" + name
					+ "\", name has already been registered for "
					+ lookupAbilitySet(name));
			return null;

		}
	}
	
	public static AbilitySet selectRandomAbilitySet() {
		return RandomUtils.selectRandomWeightedItem(abilitySets.values());
		
	}
	
	public static AbilitySet selectRandomAbilitySet(World world) {
		return RandomUtils.selectRandomWeightedItem(world.rand, abilitySets.values());
	}
	
	public static AbilitySet lookupAbilitySet(int id) {
		if (abilitySetIds.containsKey(id)) {
			return abilitySets.get(abilitySetIds.get(id));
		} else return null;
	}

	public static AbilitySet lookupAbilitySet(String name) {
		AbilitySet set = abilitySets.get(name);
		
		if (set == null) {
			
			set = abilitySets.get(name.replaceFirst("ability.", ""));
			
		}
		
		return set;
	}

	public static boolean abilitySetExists(AbilitySet abs) {
		return abilitySets.containsValue(abs);
	}

	public static int getAbilitySetCount() {
		return abilitySetsCount;
	}

}
