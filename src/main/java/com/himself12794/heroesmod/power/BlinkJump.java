package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerInstant;

public class BlinkJump extends PowerInstant {

	public BlinkJump() {
		setUnlocalizedName("blinkJump");
		setPreparationTime(20);
		setCost(20 * 5);
		setRange(75);
	}	
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		
		if ( target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK ) {
			
			BlockPos one = target.getBlockPos().offset(target.sideHit);
			BlockPos two = one.up();
			BlockPos three = one.down();
			boolean shouldJump = false;
			
			if (world.isAirBlock(one) && world.isAirBlock(two)) {
				shouldJump = true;
			} else if (world.isAirBlock(one) && world.isAirBlock(three)) {
				shouldJump = true;
				one = three;
			}
			
			if (shouldJump) {
				caster.setPositionAndUpdate(one.getX(), one.getY(), one.getZ());
				return true;
			}
			
		} else if ( target.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY ) {
			
			Vec3 move = target.hitVec;
			
			caster.setPositionAndUpdate(move.xCoord, move.yCoord, move.zCoord);
			
			return true;
		}
		
		
		return false;
	}
	
	
	
}
