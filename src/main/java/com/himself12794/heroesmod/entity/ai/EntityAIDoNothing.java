package com.himself12794.heroesmod.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.himself12794.heroesmod.PowerEffects;

public class EntityAIDoNothing extends EntityAIBase {

	
	private EntityLiving entityLiving;
    public EntityAIDoNothing(EntityLiving entity)
    {
        entityLiving = entity;
        this.setMutexBits(1);
    }

	@Override
	public boolean shouldExecute() {
		
		return PowerEffects.paralysis.isEffecting(entityLiving);
		
	}
	
    public void updateTask() {

    	//entityLiving.moveForward = 0;
    	//entityLiving.moveStrafing = 0;
    	entityLiving.getNavigator().clearPathEntity();
    	
    }

}
