package com.himself12794.heroesmod.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectsEntity;

public class PowerEffectHandler {

	@SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
		if (EffectsEntity.get(event.entityLiving).isAffectedBy(PowerEffects.rapidCellularRegeneration)) {

			if (event.source != DamageSource.outOfWorld
					&& !EffectsEntity.get(event.entityLiving).isAffectedBy(PowerEffect.negated)) {
				event.entityLiving.setHealth(0.5F);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void allowBreakBlockFist(PlayerEvent.HarvestCheck event) {

		boolean canBreak = true;
		
		if (event.entityPlayer.getHeldItem() != null) {
			Item item = event.entityPlayer.getHeldItem().getItem();
			canBreak = item == null ? true : !item.isDamageable();
		}

		EffectsEntity wrapper = EffectsEntity.get(event.entityPlayer);
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
			canBreak |= item instanceof ItemFlintAndSteel;
		}

		if (EffectsEntity.get(event.entityPlayer)
				.isAffectedBy(PowerEffects.breakFx) && canBreak) {
			float speed1 = Items.iron_pickaxe.getStrVsBlock(null,
					event.state.getBlock());
			float speed2 = Items.iron_axe.getStrVsBlock(null,
					event.state.getBlock());
			float speed3 = Items.iron_shovel.getStrVsBlock(null,
					event.state.getBlock());
			float speed = speed1 > speed2 ? (speed1 > speed3 ? speed1 : speed3)
					: (speed2 > speed3 ? speed2 : speed3);
			speed *= 0.20F;
			if (speed > event.originalSpeed)
				event.newSpeed = speed;
		}

	}
	
	@SubscribeEvent
	public void deflectProjectile(LivingAttackEvent event) {
		
		if (EffectsEntity.get(event.entityLiving).isAffectedBy(PowerEffects.telekineticShield)) {
			
			if (event.entity.worldObj.rand.nextInt(4) == 0) {
				
				if (event.source.isProjectile()) { 
					event.setCanceled(true);
					
					if (event.source instanceof EntityDamageSourceIndirect) {
						EntityDamageSourceIndirect source = (EntityDamageSourceIndirect)event.source;
						Entity initiatedBy = source.getEntity();
						Entity damagedBy = source.getSourceOfDamage();
						
						if (damagedBy instanceof EntityArrow && initiatedBy instanceof EntityLivingBase) {
							
							NBTTagCompound tags = new NBTTagCompound();
							EntityArrow arrow = (EntityArrow)damagedBy;
							
							int pickedUp = arrow.canBePickedUp;
							arrow.setDead();
							Vec3 look = event.entityLiving.getLookVec();
							
							EntityArrow returned = new EntityArrow(event.entityLiving.worldObj, event.entityLiving, 1.0F);
							returned.canBePickedUp = pickedUp;
							
							event.entityLiving.worldObj.spawnEntityInWorld(returned);
							event.entityLiving.swingItem();
							
						}
						
					}
				}
				
			}
			
		}
		
		if (EffectsEntity.get(event.entityLiving).isAffectedBy(PowerEffects.immortality)) {
			
			if (!event.source.canHarmInCreative()) {
				
				event.setCanceled(true);
				
			}
			
		}
		
	}

}
