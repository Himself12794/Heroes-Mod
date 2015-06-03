package com.himself12794.heroesmod.events;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.powerfx.PowerEffects;

public class PowerEffectHandler {
	
	@SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
		if (PowerEffects.rapidCellularRegeneration.isEffecting(event.entityLiving)) {
			if (event.source != DamageSource.outOfWorld) {
				event.entityLiving.setHealth(0.5F);
				PowerEffects.paralysis.addTo(event.entityLiving, 20 * 10, event.entityLiving);
				event.setCanceled(true);
			}
		}
	}
	
}
