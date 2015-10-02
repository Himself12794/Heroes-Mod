package com.himself12794.heroesmod;

import java.io.File;
import java.util.Map;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.google.common.collect.Maps;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.util.Reference;

public class ModConfig {

	/**
	 * This bit of hackery allows as to set the default weight when we
	 * initialize the ability, and have that be static even if the config changes
	 */
	private final Map defaultWeights = Maps.newHashMap();
	final Configuration mainConfig;
	final ConfigCategory generalModConfig;
	final ConfigCategory abilities;
	private int flamethrowing;
	private boolean enderSoundSwap;
	private boolean registeredDimension;
	private boolean explosionHealingEnabled;
	private int customDimensionId;

	public ModConfig(File file) {
		mainConfig = new Configuration(file, true);
		
		generalModConfig = mainConfig.getCategory(Reference.NAME + " Config");
		generalModConfig.setLanguageKey("heroesmod.config.general");
		generalModConfig.setComment("General " + Reference.NAME + " Configuration");
		
		abilities = mainConfig.getCategory("Ability Weights");
		abilities.setLanguageKey("heroesmod.config.weights");
		abilities.setComment("Determine weight of learning ability when first joining world. Higher value means higher chance");
		
		syncConfig();
	}

	public void syncConfig() {

		flamethrowing = mainConfig.getInt("FlamethrowingGriefing", generalModConfig.getName(), 4, 0, 4,
						"Griefing level for the flames power. 0=Entities, 1=Grass, 2=Grass and Leaves, 3=Burnable Objects, 4=Everything");
		
		enderSoundSwap = mainConfig.getBoolean("EnderSoundSwapEnabled", generalModConfig.getName(), true, "Swaps ender scream for banshee scream");
		
		explosionHealingEnabled = mainConfig.getBoolean("ExplosionHealingEnabled", generalModConfig.getName(), false, "The world heals from explosions");

		for (AbilitySet set : AbilitySet.abilitySets.values()) {
			
			// The original weights are stored first time around. This way, the defaults won't change
			if (!defaultWeights.containsKey(set)) {
				defaultWeights.put(set, set.getWeight());
			}

			float defaultWeight = (Float) defaultWeights.get(set);

			set.setWeight(mainConfig.getFloat(set.getLoggerFriendlyName(), abilities.getName(), defaultWeight, 0.0F, 20.0F,
					set.getDescription()));

		}

		if (mainConfig.hasChanged()) mainConfig.save();
	}
	
	public void registerDimensionId() {
		
		if (!this.registeredDimension) {
			customDimensionId = DimensionManager.getNextFreeDimId();
			DimensionManager.registerDimension(customDimensionId, 1);
		}
		
	}

	@SubscribeEvent
	public void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {

		if (event.modID.equals(Reference.MODID)) {
			syncConfig();
		}

	}
	
	public static int getCustomDimensionId() {
		return get().customDimensionId;
	}
	
	public static ModConfig get() {
		return HeroesMod.config();
	}
	
	public static int getFlamethrowingLevel() {
		return get().flamethrowing;
	}
	
	public boolean isExplosionHealingEnabled() {
		return get().explosionHealingEnabled;
	}
	
	public boolean isEnderSoundSwapEnabled() {
		return get().enderSoundSwap;
	}

}
