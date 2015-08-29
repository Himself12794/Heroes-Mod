package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;

import com.himself12794.heroesmod.entity.ai.EntityAIDoNothing;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class Paralysis extends PowerEffect {

	private static final String NAME = "paralysis";
	
	public Paralysis() {
		setUnlocalizedName(NAME);
		setType(EffectType.MALICIOUS);
	}

	@Override
	public void onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster, Power power) {
		
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
	
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power){
		
		if (entity instanceof EntityLiving) {
			EntityLiving target = (EntityLiving)entity;
			target.tasks.removeTask(new EntityAIDoNothing(target));
		} else if (entity instanceof EntityPlayer) {
			entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D);
		}
		
	}
	
	

}
