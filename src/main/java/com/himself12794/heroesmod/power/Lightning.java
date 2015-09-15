package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerInstant;

public class Lightning extends PowerInstant {
	
	public Lightning() {
		setPower(5.0F);
		setCoolown(80);
		setUnlocalizedName("lightning");
	}
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		if (!(target.entityHit instanceof EntityLivingBase)) return false;
		
		EntityLivingBase entity = (EntityLivingBase) target.entityHit;
		EntityLightningBolt bolt = new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ);
		boolean flag = world.addWeatherEffect(bolt);
		
		if (flag && !entity.isDead) entity.setLastAttacker(caster);
		
		return flag;
	}

}
