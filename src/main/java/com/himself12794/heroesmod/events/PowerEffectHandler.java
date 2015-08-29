package com.himself12794.heroesmod.events;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.DataWrapperP;

public class PowerEffectHandler {
	
	/*@SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
		if (PowerEffects.rapidCellularRegeneration.isAffecting(event.entityLiving)) {
			
			if (event.source != DamageSource.outOfWorld && !PowerEffect.negated.isAffecting(event.entityLiving)) {
				event.entityLiving.setHealth(0.5F);
				//PowerEffects.paralysis.addTo(event.entityLiving, 20 * 10, event.entityLiving);
				event.setCanceled(true);
			}
		}
	}*/
	
	@SubscribeEvent
	public void allowBreakBlockFist(PlayerEvent.HarvestCheck event) {
		
		boolean canBreak = true;
		
		if (event.entityPlayer.getHeldItem() != null) {
			Item item = event.entityPlayer.getHeldItem().getItem();
			canBreak = item == null ? true : !item.isDamageable();
		}
		
		DataWrapperP wrapper = DataWrapperP.get(event.entityPlayer);
		
		if (wrapper.isAffectedBy(PowerEffects.breakFx) && canBreak) {
			event.success = Items.iron_pickaxe.canHarvestBlock(event.block);
		}
		
	}
	
	@SubscribeEvent
	public void increaseBreakSpeed(PlayerEvent.BreakSpeed event) {
		
		boolean canBreak = true;
		
		if (event.entityPlayer.getHeldItem() != null) {
			Item item = event.entityPlayer.getHeldItem().getItem();
			canBreak = item == null ? true : !item.isDamageable();
		}
		
		if (DataWrapperP.get(event.entityPlayer).isAffectedBy(PowerEffects.breakFx) && canBreak) {
			float speed1 = Items.iron_pickaxe.getStrVsBlock(null, event.state.getBlock());	
			float speed2 = Items.iron_axe.getStrVsBlock(null, event.state.getBlock());
			float speed = speed1 > speed2 ? speed1 : speed2;
			if (speed > event.originalSpeed) event.newSpeed = speed * 0.50F;
		}
		
	}
	
}
