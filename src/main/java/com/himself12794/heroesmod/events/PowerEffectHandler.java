package com.himself12794.heroesmod.events;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.DataWrapper;
import com.himself12794.powersapi.util.DataWrapperP;

public class PowerEffectHandler {

	@SubscribeEvent
	public void preventDeath(LivingDeathEvent event) {
		if (PowerEffects.rapidCellularRegeneration
				.isAffecting(event.entityLiving)) {

			if (event.source != DamageSource.outOfWorld
					&& !PowerEffect.negated.isAffecting(event.entityLiving)) {
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

		DataWrapperP wrapper = DataWrapperP.get(event.entityPlayer);

		if (wrapper.powerEffectsData.isAffectedBy(PowerEffects.breakFx)
				&& canBreak) {
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

		if (DataWrapperP.get(event.entityPlayer).powerEffectsData
				.isAffectedBy(PowerEffects.breakFx) && canBreak) {
			float speed1 = Items.iron_pickaxe.getStrVsBlock(null,
					event.state.getBlock());
			float speed2 = Items.iron_axe.getStrVsBlock(null,
					event.state.getBlock());
			float speed3 = Items.iron_shovel.getStrVsBlock(null,
					event.state.getBlock());
			float speed = speed1 > speed2 ? (speed1 > speed3 ? speed1 : speed3)
					: (speed2 > speed3 ? speed2 : speed3);
			speed *= 0.75F;
			if (speed > event.originalSpeed)
				event.newSpeed = speed;
		}

	}
	
	@SubscribeEvent
	public void deflectProjectile(LivingAttackEvent event) {
		
		if (DataWrapper.get(event.entityLiving).powerEffectsData.isAffectedBy(PowerEffects.telekineticShield)) {
			
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
		
		if (DataWrapper.get(event.entityLiving).powerEffectsData.isAffectedBy(PowerEffects.immortality)) {
			
			if (!event.source.canHarmInCreative()) {
				
				event.setCanceled(true);
				
			}
			
		}
		
	}

}
