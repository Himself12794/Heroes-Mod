package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectContainer;
import com.himself12794.powersapi.storage.EffectsEntity;

public class Flight extends PowerEffect {
	
	private static final String NAME = "flight";
	
	public Flight() {
		setUnlocalizedName(NAME);
	}
	
	@Override
	public void onApplied(EntityLivingBase entity, EntityLivingBase caster, EffectContainer effectContainer) {
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			player.capabilities.allowFlying = true;
			
		}
		
	}
	
	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power){
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			if (!EffectsEntity.get(entity).isCreativePlayer()) player.capabilities.allowFlying = false;
			
		} 
		
	}

}
