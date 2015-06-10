package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.entity.EntitySpell;
import com.himself12794.powersapi.power.IHomingPower;
import com.himself12794.powersapi.power.PowerRanged;

public class DummyHoming extends PowerRanged implements IHomingPower {
	
	public DummyHoming() {
		setPower(2.0F);
		setCoolDown(20);
		setDuration(0);
		setUnlocalizedName("dummyHoming");
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {

		if (target.entityHit != null) {
			target.entityHit.attackEntityFrom(DamageSource.magic, getPower());
		}
		return true;
	}
	
	public float getSpellVelocity(){
		return 2.0F;
	}

	@Override
	public MovingObjectPosition getTarget(EntitySpell spell, MovingObjectPosition target) {

		return null;
	}

}
