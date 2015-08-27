package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.DataWrapper;
import com.himself12794.powersapi.util.UsefulMethods;

public class Telekinesis extends PowerInstant {

	public Telekinesis() {

		setPower(0.0F);
		setMaxConcentrationTime(10 * 20);
		setCoolDown(60);
		setDuration(15 * 20);
		setUnlocalizedName("telekinesis");

	}

	@Override
	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {
		
		DataWrapper.get(caster).setSecondaryPower(Powers.SLAM);

		if (target.entityHit != null) {
			
			if (target.entityHit.getDistanceToEntity(caster) > 5.0F) {
			
				Vec3 look = caster.getLookVec();
	
				target.entityHit.setVelocity(-look.xCoord * 1.25F, -look.yCoord * 1.25F, -look.zCoord * 1.25F);
			} else {
				target.entityHit.setVelocity(0.0D, 0.0D, 0.0D);
			}
		
			return true;
		}

		return false;

	}

	public boolean onFinishedCastingEarly(ItemStack stack, World world,
			EntityPlayer playerIn, int timeLeft, MovingObjectPosition pos) {

		
		if (pos.entityHit != null) {
			pos.entityHit.setVelocity(0.0D, 0.0D, 0.0D);
		}

		return true;
	}

	public boolean onFinishedCasting(ItemStack stack, World world,
			EntityPlayer caster, MovingObjectPosition pos) {

		if (pos.entityHit != null) {
			pos.entityHit.setVelocity(0.0D, 0.0D, 0.0D);
		}

		return true;
	}

}
