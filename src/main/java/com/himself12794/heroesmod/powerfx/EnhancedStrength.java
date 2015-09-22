package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

import com.himself12794.powersapi.power.PowerEffect;

public class EnhancedStrength extends PowerEffect {
	
	private static final String NAME = "enhancedStrength";
	
	public EnhancedStrength() {
		setUnlocalizedName(NAME);
	}

	@Override
	public float onAttack(EntityLivingBase target, DamageSource damageSource, float amount, EntityLivingBase caster) {
		
		amount += 4.0F;
		
		float j = 5.0F;
		target.addVelocity((double)(-MathHelper.sin(caster.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.cos(caster.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F));
		
		return amount;
	}

}
