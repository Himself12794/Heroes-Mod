package com.himself12794.heroesmod.proxy;

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
import com.himself12794.heroesmod.ModConfig;
import com.himself12794.heroesmod.PowerEffects;
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
		
		ModConfig.loadConfig(event);
		
		HeroesNetwork.init(NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID + " NetChannel" ));
		HeroesNetwork.registerMessages();

	}

	public void init(FMLInitializationEvent event) {		
		ModItems.addItems();
		
		FMLCommonHandler.instance().bus().register( new ModConfig() );
	}

	public void postinit(FMLPostInitializationEvent event) {
			
		PowerEffectsRegistration.registerEffects();
		HeroesMod.logger().info("Registered " + PowerEffects.class.getDeclaredFields().length + " power effects");
		
		HeroesMod.logger().info("Registering powers");
		PowersRegistraton.registerPowers();
		
		HeroesMod.logger().info("Registering ability sets");
		AbilitySet.registerAbilitySets();
		
		MinecraftForge.EVENT_BUS.register(new PowerEffectHandler());
		
		HeroesMod.apiInstance().propertiesManager().registerPropertyClass(AbilitiesEntity.class, EntityPlayer.class);
		
		ModConfig.syncConfig();
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
