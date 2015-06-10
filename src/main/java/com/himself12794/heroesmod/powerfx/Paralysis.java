package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.heroesmod.entity.ai.EntityAIDoNothing;

public class Paralysis extends PowerEffect {

	public Paralysis() {}

	@Override
	public void onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster) {
		
		if (entity instanceof EntityLiving) {
			EntityLiving target = (EntityLiving)entity;
			target.tasks.addTask(1, new EntityAIDoNothing(target));
		} else if (entity instanceof EntityPlayer) {
			
			
			EntityPlayer player = (EntityPlayer)entity;
			//entity.setPosition(player.prevPosX, player.prevPosY, player.prevPosZ);
			//entity.prevPosX = entity.lastTickPosX;
			player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(-1.0D);
			
		}
				
	}
	
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster){
		
		if (entity instanceof EntityLiving) {
			EntityLiving target = (EntityLiving)entity;
			target.tasks.removeTask(new EntityAIDoNothing(target));
		} else if (entity instanceof EntityPlayer) {
			entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
		}
		
	}
	
	

}