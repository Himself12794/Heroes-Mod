package com.himself12794.heroesmod.power;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.himself12794.powersapi.PowersAPI;
import com.himself12794.powersapi.power.PowerRanged;

public class ExplodingFireball extends PowerRanged {
	
	public static final String NAME = "explodingFireball";
	
	public ExplodingFireball() {
		setPower(8.0F);
		setCoolDown(100);
		setDuration(8 * 20);
		setUnlocalizedName(NAME);
	}	
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		boolean flag = false;
		world.newExplosion(null, target.hitVec.xCoord, target.hitVec.yCoord, target.hitVec.zCoord, 3.0F, false, false);
		
		PowersAPI.logger.info("It asploded");
		
		return flag;
	}

}
