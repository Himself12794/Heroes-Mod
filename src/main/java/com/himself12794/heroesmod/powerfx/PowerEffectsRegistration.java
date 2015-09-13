package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.util.Reference;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectContainer;

public class PowerEffectsRegistration {
	
	public static void registerEffects() {
		
		PowerEffect.registerEffect(new RapidCellularRegeneration());
		PowerEffect.registerEffect(new Lift());
		PowerEffect.registerEffect(new Slam());
		PowerEffect.registerEffect(new PowerEffect("levitate"){
			
			@Override
			public boolean onUpdate(EntityLivingBase entity, int timeLeft,	EntityLivingBase caster, Power power) {
				entity.jumpMovementFactor = 0.0F;
				return true;
			}
		});
		
		PowerEffect.registerEffect(new Paralysis());
		PowerEffect.registerEffect(new EnhancedStrength());
		PowerEffect.registerEffect(new Flight());
		PowerEffect.registerEffect(new PhasingFx());
		PowerEffect.registerEffect(new Telekinesis());
		PowerEffect.registerEffect(new PowerEffect("break", true, EffectType.BENEFICIAL));
		PowerEffect.registerEffect(new PowerEffect("telekineticShield", true, EffectType.BENEFICIAL, true));
		PowerEffect.registerEffect(new PowerEffect("immortality", false, EffectType.TAG, true));
		PowerEffect.registerEffect(new EmphaticMimicry());
		PowerEffect.registerEffect(new PowerEffect("speedBoost", true, EffectType.BENEFICIAL){
			
			public boolean onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster, Power power) {

				if (UtilMethods.getTotalVelocity(entity) >= 0.0F) {
					Vec3 look = entity.getLookVec();
					Vec3 pos = entity.getPositionVector();
					pos = pos.add(entity.getLookVec());
					BlockPos block = new BlockPos(pos.xCoord, pos.yCoord, pos.zCoord);
					entity.stepHeight = UtilMethods.getDistanceToNextOccupiableSpace(entity.worldObj, block);
				}

				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, 3, false, false));
				return true;
			}
			
			public void onRemoval(final EntityLivingBase entity, final EntityLivingBase caster, final Power power){
				entity.stepHeight = 0.0F;
			}
			
			
		});
		
		
	}

}
