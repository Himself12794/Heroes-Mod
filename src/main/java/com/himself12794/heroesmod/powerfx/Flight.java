package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.power.IPlayerOnly;
import com.himself12794.powersapi.power.PowerEffect;

public class Flight extends PowerEffect implements IPlayerOnly {
	
	@Override
	public void onApplied(EntityLivingBase entity, int time, EntityLivingBase caster) {
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			if (player.capabilities.allowFlying) {
				
				player.getEntityData().getCompoundTag(Reference.MODID).setBoolean("couldFly", true);
				
			}
			
		}
		
	}
	
	@Override
	public void onUpdate(EntityPlayer entity, int timeLeft, EntityLivingBase caster) {
		
		boolean couldFly = entity.getEntityData().getCompoundTag(Reference.MODID).getBoolean("couldFly");
			
		if (!couldFly) entity.capabilities.allowFlying = true;
		
	}
	
	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster){
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			boolean couldFly = player.getEntityData().getCompoundTag(Reference.MODID).getBoolean("couldFly");
			
			if (!couldFly) player.capabilities.allowFlying = false;
			
		} 
		
	}

}
