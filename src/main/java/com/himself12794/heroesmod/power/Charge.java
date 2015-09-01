package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.DataWrapper;

public class Charge extends PowerInstant {

	public Charge() {
		setUnlocalizedName("charge");
		setMaxConcentrationTime(0);
		setRange(40);
		setPower(10.0F);
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		
		if (target.entityHit instanceof EntityLivingBase) {
			Vec3 move = target.hitVec;
			//move = move.normalize();
			System.out.println(move);
			DataWrapper.get(caster).powerEffectsData.addPowerEffect(PowerEffects.immortality, 10, caster, this);
			caster.setPosition(move.xCoord , move.yCoord, move.zCoord);
			caster.setCustomNameTag("The Beast");
			caster.motionY = 0.25D;
			
			move = caster.getLookVec();			
			target.entityHit.motionX = move.xCoord * 6.0D;
			target.entityHit.motionY = move.yCoord * 8.0D;
			target.entityHit.motionZ = move.zCoord * 6.0D;
			((EntityLivingBase)target.entityHit).attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) caster), getPower());

			
			return true;
		}
		return false;
		
	}

}
