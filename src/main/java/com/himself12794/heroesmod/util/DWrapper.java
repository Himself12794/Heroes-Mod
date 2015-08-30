package com.himself12794.heroesmod.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

import com.himself12794.powersapi.util.DataWrapper;

public class DWrapper extends DataWrapper {
	
	private static final String POWER_DATA = "power.data";

	protected DWrapper(EntityLivingBase entity) {
		super(entity);
	}
	
	public static DWrapper get(EntityLivingBase entity) {
		return new DWrapper(entity);
	}
	
	public static DWrapper set(EntityLivingBase entity, NBTTagCompound tag) {
		
		entity.getEntityData().setTag(Reference.MODID, tag);
		return new DWrapper(entity);
		
	}
	
	public NBTTagCompound getHeroesModData() {
		
		NBTTagCompound nbt = getSubModData();
		NBTTagCompound data = null;
		
		if (nbt.hasKey(Reference.MODID, 10)) {
			data = nbt.getCompoundTag(Reference.MODID);
		} else {
			data = new NBTTagCompound();
			nbt.setTag(Reference.MODID,	data);
		}
		
		return data;
		
	}
	
	public void setSavedBlockPos1(BlockPos pos) {
		
		int[] data;
		
		if (pos != null) {
			
			data = new int[] {pos.getX(), pos.getY(), pos.getZ()};
		} else {
			data = new int[] {0, 0, 0};
		}
		
		getModEntityData().setIntArray(POWER_DATA, data);
		
	}
	
	public BlockPos getSavedBlockPos1(){
		
		int[] data = getModEntityData().getIntArray(POWER_DATA);
		
		if (data.length != 3) {
			return BlockPos.ORIGIN;
		} else {
			return new BlockPos(data[0], data[1],data[2]);
		}
		
	}

}
