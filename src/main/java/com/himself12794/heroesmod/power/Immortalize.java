package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.util.DataWrapper;

public class Immortalize extends PowerEffectActivatorBuff {
	
	public Immortalize() {
		
		setUnlocalizedName("immortalize");
		
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		if (target.entityHit != null) {
			if (target.entityHit instanceof EntityLivingBase) PowerEffects.rapidCellularRegeneration.addTo((EntityLivingBase) target.entityHit, -1, caster);
		}
		return false;
	}
	
	public boolean onCastAdditional(World world, EntityLivingBase caster, ItemStack stack, float modifier) {
		
		DataWrapper data = DataWrapper.get(caster);
		data.teachPower(Powers.FLAMES).setPrimaryPower(Powers.FLAMES);
		data.teachPower(Powers.EXPLODING_BOLT).setSecondaryPower(Powers.EXPLODING_BOLT);
		
		return true;
	}

	@Override
	public PowerEffect getPowerEffect() {
		return PowerEffects.lift;
	}

	@Override
	public int getEffectDuration() {
		return 100;
	}

}
