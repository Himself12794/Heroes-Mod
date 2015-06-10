package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerInstant;

public class Incinerate extends PowerInstant {
	
	public Incinerate() {
		
		setPower(0.0F);
		setCoolDown(60);
		setDuration(15 * 20);
		setMaxConcentrationTime(100);
		setUnlocalizedName("incinerate");
		
	}
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		
		boolean flag = false;
		
		if (!target.entityHit.isImmuneToFire()) {
			
			flag = true;
			target.entityHit.setFire(MathHelper.ceiling_float_int(( getDuration() / 20 ) * modifier));
			
			if (!target.entityHit.isDead) ((EntityLivingBase)target.entityHit).setLastAttacker(caster);
			
		}
		
		return flag;
		
	}

}