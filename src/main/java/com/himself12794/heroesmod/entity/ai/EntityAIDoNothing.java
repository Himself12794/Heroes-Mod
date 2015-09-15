package com.himself12794.heroesmod.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.storage.PowersEntity;

public class EntityAIDoNothing extends EntityAIBase {

	private EntityLiving entityLiving;

	public EntityAIDoNothing(EntityLiving entity) {
		entityLiving = entity;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {

		return PowersEntity.get(entityLiving).getPowerEffectsData()
				.isAffectedBy(PowerEffects.paralysis);

	}

	public void updateTask() {

		// entityLiving.moveForward = 0;
		// entityLiving.moveStrafing = 0;
		entityLiving.getNavigator().clearPathEntity();

	}

}
