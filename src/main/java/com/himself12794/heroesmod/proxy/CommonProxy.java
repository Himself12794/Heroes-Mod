package com.himself12794.heroesmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.events.PowerEffectHandler;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.power.PowersRegistraton;
import com.himself12794.heroesmod.powerfx.PowerEffectsRegistration;
import com.himself12794.heroesmod.storage.AbilitiesWrapper;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.storage.PropertiesBase;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {

		HeroesNetwork.init(NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID + " NetChannel" ));
		HeroesNetwork.registerMessages();
		
		if (Loader.isModLoaded("powersAPI")) {
			
			HeroesMod.logger.info("Registering power effects");
			PowerEffectsRegistration.registerEffects();
			HeroesMod.logger.info("Registered " + PowerEffects.class.getDeclaredFields().length + " power effects");
			
			//HeroesMod.logger.info("Registering events");
			MinecraftForge.EVENT_BUS.register(new PowerEffectHandler());
			
		
		} else {
			
			HeroesMod.logger.fatal("Powers API not detected, loading cannot continue.");
			
		}

	}

	public void init(FMLInitializationEvent event) {		
		
		if (Loader.isModLoaded("powersAPI")) {
			
			HeroesMod.logger.info("Registering powers");
			PowersRegistraton.registerPowers();
			
			HeroesMod.logger.info("Registering ability sets");
			AbilitySet.registerAbilitySets();
			
			PropertiesBase.registerPropertyClass(AbilitiesWrapper.class);
		
		} else {
			
			HeroesMod.logger.fatal("Powers API not detected, loading cannot continue.");
			
		}
		
	}
	
	public Side getSide() {
		return Side.SERVER;
	}

	public void postinit(FMLPostInitializationEvent event) {
	}
	
	public EntityPlayer getPlayer() {
		return null;
	}

}
