package com.himself12794.heroesmod.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.events.PowerEffectHandler;
import com.himself12794.powersapi.power.PowerEffect;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		if (Loader.isModLoaded("powersAPI")) {
			
			HeroesMod.logger.info("Loading Powers");
			Powers.registerPowers();
			//ModItems.addItems();
		
		} else {
			
			HeroesMod.logger.fatal("Powers API not detected, loading cannot continue.");
			
		}
	}

	public void init(FMLInitializationEvent event) {
		if (Loader.isModLoaded("powersAPI")) {
			//HeroesMod.logger.info("Registering power effects");
			PowerEffects.registerEffects();
			HeroesMod.logger.info("Registered " + PowerEffects.class.getDeclaredFields().length + " power effects");
			
			HeroesMod.logger.info("Registering events");
			MinecraftForge.EVENT_BUS.register(new PowerEffectHandler());
			
			HeroesMod.logger.info("Registering ability sets");
			AbilitySet.registerAbilitySets();
		
		} else {
			
			HeroesMod.logger.fatal("Powers API not detected, loading cannot continue.");
			
		}
		
	}
	
	public Side getSide() {
		return Side.SERVER;
	}

	public void postinit(FMLPostInitializationEvent event) {
		
	}

}
