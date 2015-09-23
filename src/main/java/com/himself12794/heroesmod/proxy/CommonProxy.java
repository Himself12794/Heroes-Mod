package com.himself12794.heroesmod.proxy;

import java.lang.reflect.Method;

import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.events.PowerEffectHandler;
import com.himself12794.heroesmod.item.ModItems;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.power.PowersRegistraton;
import com.himself12794.heroesmod.powerfx.PowerEffectsRegistration;
import com.himself12794.heroesmod.storage.AbilitiesEntity;
import com.himself12794.heroesmod.util.Reference;

public class CommonProxy {

	public void preinit(FMLPreInitializationEvent event) {
		
		HeroesNetwork.init(NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID + " NetChannel" ));
		HeroesNetwork.registerMessages();

	}

	public void init(FMLInitializationEvent event) {
		
		ModItems.addItems();
		
		FMLCommonHandler.instance().bus().register( HeroesMod.config() );
	}

	public void postinit(FMLPostInitializationEvent event) {
			
		HeroesMod.apiInstance().propertiesHandler().registerPropertyClass(AbilitiesEntity.class, EntityPlayer.class, Reference.MODID + ":abilitiesWrapper");
		
		PowerEffectsRegistration.registerEffects();
		
		PowersRegistraton.registerPowers();
		
		AbilitySet.registerAbilitySets();
		
		MinecraftForge.EVENT_BUS.register(new PowerEffectHandler());
		
		HeroesMod.logger().info("Registered " + PowerEffects.class.getDeclaredFields().length + " power effects");
		HeroesMod.logger().info("Registered " + Powers.class.getDeclaredFields().length + " powers");
		HeroesMod.logger().info("Registered " + AbilitySet.getAbilitySetCount() + " ability sets");
		
		HeroesMod.config().syncConfig();
	}
	
	public Side getSide() {
		return Side.SERVER;
	}

	public EntityPlayer getPlayerFromContext(MessageContext ctx) {
		if (ctx.side.isServer()) {
			return ctx.getServerHandler().playerEntity;
		} else {
			return null;
		}
	}
	
	public void scheduleTaskBasedOnContext(MessageContext ctx, Runnable task) {
		if (ctx.side.isServer()) {
			System.out.println("Doing particles server");
			ctx.getServerHandler().playerEntity.getServerForPlayer().addScheduledTask( task );
		}
	}
	

}
