package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerRanged;

public class ExplodingFireball extends PowerRanged {
	
	public ExplodingFireball() {
		setPower(8.0F);
		setCoolDown(100);
		setDuration(8 * 20);
		setUnlocalizedName("explodingFireball");
	}	
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		boolean flag = false;
		Explosion boom = new Explosion(world, null, target.hitVec.xCoord, target.hitVec.yCoord, target.hitVec.zCoord, 3.0F, false, false);
		boom.doExplosionA();
		world.playSound(target.hitVec.xCoord, target.hitVec.yCoord, target.hitVec.zCoord, "fire.ignite", 5.0F, 1.0F, false);
		world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, target.hitVec.xCoord, target.hitVec.yCoord, target.hitVec.zCoord, 1.0D, 0.0D, 0.0D);
		
		return flag;
	}

}
