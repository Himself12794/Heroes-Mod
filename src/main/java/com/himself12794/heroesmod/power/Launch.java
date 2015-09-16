package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.world.BioticExplosion;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.storage.EffectsEntity;

public class Launch extends PowerBuff {

	public Launch() {
		super("launch");
	}
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		
		if(caster != null && !caster.isDead) {
			caster.motionY = 8.0D;
			BioticExplosion.doExplosion(world, caster, 2.0F, 10.0F);
			if (caster instanceof EntityPlayer) {
				EffectsEntity.get(caster).addPowerEffect(PowerEffects.flight, -1, caster, this);
				EntityPlayer player = (EntityPlayer)caster;
				if (player.capabilities.allowFlying) {
					player.capabilities.isFlying = true;
					player.setSprinting(true);
				}			
				
			}
			return true;
		}
		
		return false;
		
	}

}
