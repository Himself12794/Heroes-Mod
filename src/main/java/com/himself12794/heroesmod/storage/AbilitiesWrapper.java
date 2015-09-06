package com.himself12794.heroesmod.storage;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.storage.PropertiesBase;

public class AbilitiesWrapper extends PropertiesBase {
	
	private static final String ABILITIES_SET = Reference.MODID + ":abilitiesSet";

	private final Set<AbilitySet> abilitySets = Sets.newHashSet();
	
	
	protected AbilitiesWrapper(EntityLivingBase entity) {
		super(ABILITIES_SET, entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		
		

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public PropertiesBase resetForRespawn() {
		// TODO Auto-generated method stub
		return null;
	}

}
