package com.himself12794.heroesmod;

import java.util.Map;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.google.common.collect.Maps;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.util.Reference;

public class ModConfig {

	/**
	 * This bit of hackery allows as to set the default weight when we
	 * initialize the ability, and have that be static even if the config changes
	 */
	private static final Map defaultWeights = Maps.newHashMap();
	public static Configuration config;
	public static ConfigCategory generalModConfig;
	public static ConfigCategory abilities;
	public static int flamethrowing;
	public static boolean enderSoundSwap;

	public static void loadConfig(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile(), true);
		
		generalModConfig = config.getCategory(Reference.NAME + " Config");
		generalModConfig.setLanguageKey("heroesmod.config.general");
		generalModConfig.setComment("General " + Reference.NAME + " Configuration");
		
		abilities = config.getCategory("Ability Weights");
		abilities.setLanguageKey("heroesmod.config.weights");
		abilities.setComment("Determine weight of learning ability when first joining world. Higher value means higher chance");
		
		syncConfig();
	}

	public static void syncConfig() {

		flamethrowing = config.getInt("FlamethrowingGriefing", generalModConfig.getName(), 0, 0, 4,
						"Griefing level for the flames power. 0=Entities, 1=Grass, 2=Grass and Leaves, 3=Burnable Objects, 4=Everything");
		
		enderSoundSwap = config.getBoolean("EnderSoundSwapEnabled", generalModConfig.getName(), true, "Swaps ender scream for banshee scream");

		for (AbilitySet set : AbilitySet.abilitySets.values()) {
			
			// The original weights are stored first time around. This way, the defaults won't change
			if (!defaultWeights.containsKey(set)) {
				defaultWeights.put(set, set.getWeight());
			}

			float defaultWeight = (Float) defaultWeights.get(set);

			set.setWeight(config.getFloat(set.getRegisteredName(), abilities.getName(), defaultWeight, 0.0F, 20.0F,
					set.getDescription()));

		}

		if (config.hasChanged()) config.save();
	}

	@SubscribeEvent
	public void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {

		if (event.modID.equals(Reference.MODID)) {
			syncConfig();
		}

	}

}
