package com.himself12794.heroesmod.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.DataWrapper;

public class PowerEffectHandler {
	
	@SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
		if (PowerEffects.rapidCellularRegeneration.isEffecting(event.entityLiving)) {
			
			if (event.source != DamageSource.outOfWorld && !PowerEffect.negated.isEffecting(event.entityLiving)) {
				event.entityLiving.setHealth(0.5F);
				//PowerEffects.paralysis.addTo(event.entityLiving, 20 * 10, event.entityLiving);
				event.setCanceled(true);
			}
		}
	}
	
	/*@SubscribeEvent
	public void enhancedStrength(LivingHurtEvent event) {
		
		Entity entity = event.source.getEntity();
		
		if (entity != null) {
			
			if (entity instanceof EntityLivingBase) {
				
				EntityLivingBase attacker = (EntityLivingBase)entity;
				
				if (PowerEffects.enhancedStrength.isEffecting((EntityLivingBase) entity)) {
					
					if (event.source.damageType.equals("player")) {
						
						System.out.println("puncha!");
						
						event.ammount += 4.0F;
						float j = 5.0F;
						event.entityLiving.addVelocity((double)(-MathHelper.sin(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.cos(attacker.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F));
						
					}
					
				}
				
			}
		}
		
	}*/
	
}
