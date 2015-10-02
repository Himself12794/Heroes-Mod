package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerRanged;

public class Dummy extends PowerRanged {
	
	public Dummy() {
		setPower(0.0F);
		setCost(20);
		setDuration(0);
		setUnlocalizedName("dummy");
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		return true;
	}
	
	public float getSpellVelocity(){
		return 2.0F;
	}
}
