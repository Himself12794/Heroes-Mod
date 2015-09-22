package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class RapidCellularRegeneration extends PowerEffect {
	
	private static final String name = "rapidCellularRegeneration";
	
	public RapidCellularRegeneration() {
		setUnlocalizedName(name);
		setType(EffectType.BENEFICIAL);
		setNegateable();
		setPersistant();
	}
	
	@Override
	public boolean onUpdate(EntityLivingBase entity, int timeleft, EntityLivingBase caster, Power power ) {
		
		entity.removePotionEffect(Potion.poison.id);
		entity.removePotionEffect(Potion.hunger.id);
		entity.removePotionEffect(Potion.wither.id);
		
		if (entity.getHealth() < entity.getMaxHealth() && entity.posY > -5.0D) {
			//entity.setHealth(entity.getHealth() + entity.getMaxHealth() * 0.1F);
			entity.heal(entity.getMaxHealth() * 0.1F);
		}
		
		return true;
		
	}

	@Override
	public void onRemoval(EntityLivingBase entity, EntityLivingBase caster, Power power) {
		
		if (entity instanceof EntityPlayer) {
		
			if (!((EntityPlayer)entity).capabilities.isCreativeMode) entity.addPotionEffect(new PotionEffect(Potion.wither.id, 20 * 60 * 5, 3));
		}
	}
	
	
	
}
