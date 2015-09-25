package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.storage.EffectsEntity;

public class Heal extends PowerBuff {
	
	public Heal() {
		super("heal");
		setDuration(0);
		setPower(0.25F);
		setCost(1);
		//setType(SpellType.BUFF);
	} 
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		boolean flag = false;
		if (caster.getHealth() < caster.getMaxHealth()) {
			flag = true;
			caster.heal(getPower(modifier) * modifier);
		}
		
		return flag;
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caste, float modifier, int state ) {
		boolean flag = false;
		EntityLivingBase entity = null;
		EffectsEntity.get(caste).addPowerEffect(PowerEffects.slam, 100, caste, this);
		if (target.entityHit instanceof EntityLivingBase)
			 entity = ((EntityLivingBase)target.entityHit);
			if(entity.getHealth() < entity.getMaxHealth()) {
				flag = true;
				entity.heal(getPower(modifier) * modifier);
			}
		
		return flag;
	}	
	
}
