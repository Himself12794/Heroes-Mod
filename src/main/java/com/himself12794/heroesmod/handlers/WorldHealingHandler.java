package com.himself12794.heroesmod.handlers;

import java.util.Map;

import net.minecraft.world.Explosion;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.google.common.collect.Maps;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.ModConfig;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.heroesmod.world.NoDropsExplosion;
import com.himself12794.heroesmod.world.WorldHealing;

public class WorldHealingHandler {

	private final Map<WorldServer, WorldHealing> worldHealers = Maps.newHashMap(); 

	public Map<WorldServer, WorldHealing> getWorldHealers() {
		return worldHealers;
	}
	
	public WorldHealing getWorldHealerForWorld(WorldServer server) {
		return worldHealers.get(server);
	}
	
	@SubscribeEvent
	public void onLoad(WorldEvent.Load event) {
		
		if (!event.world.isRemote) {
			worldHealers.put((WorldServer) event.world, new WorldHealing(Reference.MODID + ":WorldHealing"));
		}
		
	}
	
	@SubscribeEvent
	public void onUnload(WorldEvent.Unload event) {
		
		if (!event.world.isRemote && worldHealers.containsKey(event.world)) {
			worldHealers.remove(event.world);
		}
		
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event) {
		
		if (!event.world.isRemote && worldHealers.containsKey(event.world) && event.phase == TickEvent.Phase.START) {
			
			WorldHealing healer = worldHealers.get(event.world);
			healer.doHeal((WorldServer) event.world);
		}
	}

	@SubscribeEvent
	public void handleExplosion(ExplosionEvent.Start event) {
		
		if (event.explosion.getClass().equals(Explosion.class) && !event.world.isRemote && ModConfig.get().isExplosionHealingEnabled()) {
			event.setCanceled(true);
			
			try {
				NoDropsExplosion newExplosion = new NoDropsExplosion(event.explosion);				
				
				newExplosion.doExplosionA();
				newExplosion.doExplosionB(true);
				HeroesNetwork.client().explosionParticles(newExplosion);
				
				getWorldHealerForWorld((WorldServer) event.world).addExplosion(newExplosion);
				
			} catch (Exception e) {
				HeroesMod.logger().error("An error ocurred in replacing explosion", e);
			} 
		}
		
	}
	
}
