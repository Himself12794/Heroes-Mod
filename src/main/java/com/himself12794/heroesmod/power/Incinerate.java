package com.himself12794.heroesmod.power;

import net.minecraft.block.BlockTNT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.UsefulMethods;

public class Incinerate extends PowerInstant {
	
	public Incinerate() {
		
		setPower(0.0F);
		setCost(60);
		setDuration(15 * 20);
		setUnlocalizedName("incinerate");
		
	}
	
	@Override
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		if(UtilMethods.checkLiving(target.entityHit)) {
		
			if (!target.entityHit.isImmuneToFire()) {
				
				target.entityHit.setFire(MathHelper.ceiling_float_int(( getDuration() / 20 ) * modifier));
				
				if (!target.entityHit.isDead) ((EntityLivingBase)target.entityHit).setLastAttacker(caster);
				return true;
				
			}
		
		} else if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && caster instanceof EntityPlayer) {
			
			BlockPos pos = target.getBlockPos();
			if (UsefulMethods.getBlockAtPos(pos, world) == Blocks.tnt) {
				BlockTNT tnt = (BlockTNT)Blocks.tnt;
				
				tnt.explode(world, pos, tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)), caster);
				world.setBlockToAir(pos);
				
				return true;
			}
			
		}
		
		return false;
		
	}

}
