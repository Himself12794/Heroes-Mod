package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.powersapi.power.IPlayerOnly;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectContainer;
import com.himself12794.powersapi.storage.EffectsWrapper;

public class Flight extends PowerEffect implements IPlayerOnly {
	
	private static final String NAME = "flight";
	
	public Flight() {
		setUnlocalizedName(NAME);
	}
	
	@Override
	public void onApplied(EntityLivingBase entity, EntityLivingBase caster, EffectContainer effectContainer) {
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			effectContainer.getDataTag().setBoolean("couldFly", player.capabilities.allowFlying);
			player.capabilities.allowFlying = true;

			
		}
		
	}
	
	@Override
	public void onUpdate(EntityPlayer entity, int timeLeft, EntityLivingBase caster) {
		
		boolean couldFly = EffectsWrapper.get(caster).getEffectContainer(this).getDataTag().getBoolean("couldFly");
			
		if (!couldFly) entity.capabilities.allowFlying = true;
		
	}
	
	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power){
		
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			boolean couldFly = EffectsWrapper.get(caster).getEffectContainer(this).getDataTag().getBoolean("couldFly");
			
			if (!couldFly) player.capabilities.allowFlying = false;
			
		} 
		
	}

}
