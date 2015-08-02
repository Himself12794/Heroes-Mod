package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings;

import com.himself12794.powersapi.power.IPlayerOnly;
import com.himself12794.powersapi.power.PowerEffect;

public class PhasingFx extends PowerEffect implements IPlayerOnly {
	
	private static final String NAME = "phasing";
	
	public PhasingFx() {
		setUnlocalizedName(NAME);
	}

	@Override
	public void onUpdate(EntityPlayer entity, int timeLeft, EntityLivingBase caster) {
		
		if (!entity.capabilities.allowFlying) {
			
			entity.motionY = 0.0D;
			entity.jumpMovementFactor = 0.0F;
			entity.noClip = true;
			
		} else {
			
			WorldSettings.GameType.SPECTATOR.configurePlayerCapabilities(entity.capabilities);
			
		}
		
	}

}
