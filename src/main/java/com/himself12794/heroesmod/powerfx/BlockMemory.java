package com.himself12794.heroesmod.powerfx;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.util.DWrapper;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.util.UsefulMethods;

public class BlockMemory extends PowerEffect {

	public BlockMemory() {
		setType(EffectType.TAG);
		setUnlocalizedName("blockMemory");
	}
	
	public boolean shouldApplyEffect(EntityLivingBase entityHit, EntityLivingBase caster, Power power) {
		
		if (entityHit != null && power == Powers.BLOCK_RECALL) {
			
			BlockPos pos = DWrapper.get(entityHit).getSavedBlockPos1();
			
			return pos != null;
			
		}
		
		return false;
		
	}
	
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power){
		
		if (entity != null && power == Powers.BLOCK_RECALL) {
			
			DWrapper wrap = DWrapper.get(entity);
			
			IBlockState block = entity.worldObj.getBlockState(wrap.getSavedBlockPos1());
			
		}
		
	}
	
}
