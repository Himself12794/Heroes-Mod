package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectsEntity;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

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
		//PowerEffect.registerEffect(new PhasingFx());
		PowerEffect.registerEffect(new Telekinesis());
		PowerEffect.registerEffect(new PowerEffect("break", true, EffectType.BENEFICIAL));
		PowerEffect.registerEffect(new PowerEffect("telekineticShield", true, EffectType.BENEFICIAL, true));
		PowerEffect.registerEffect(new PowerEffect("immortality", false, EffectType.TAG, true));
		PowerEffect.registerEffect(new EmphaticMimicry());
		PowerEffect.registerEffect(new PowerEffect("speedBoost", true, EffectType.BENEFICIAL){
			
			public boolean onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster, Power power) {

				PowerProfile profile = PowersEntity.get(entity).getPowerProfile(power);
				int level = profile != null  && power == Powers.speedBoost ? 
						(profile.level < 3 ? profile.level : 3) : 1;
					
				if (entity.moveForward > 1.0 && entity.getActivePotionEffect(Potion.moveSlowdown) == null) entity.setSprinting(true);
		
				if (level >= 3) {
					Vec3 look = entity.getLookVec();
					Vec3 pos = entity.getPositionVector();
					pos = pos.add(entity.getLookVec());
					BlockPos block = new BlockPos(pos.xCoord, pos.yCoord, pos.zCoord);
					entity.stepHeight = UtilMethods.getDistanceToNextOccupiableSpace(entity.worldObj, block);
				}
				
				entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, level, false, false));
				return true;
			}
			
			public void onRemoval(final EntityLivingBase entity, final EntityLivingBase caster, final Power power){
				entity.stepHeight = 0.0F;
			}
			
			
		});
		PowerEffect.registerEffect(new PowerEffect("karma", true, EffectType.MALICIOUS) {
			
			public boolean onUpdate(final EntityLivingBase entity, final int timeLeft, final EntityLivingBase caster, final Power initiatedPower){ return caster != null; }
			
			public float onDamaged(EntityLivingBase affectedEntity, EntityLivingBase casterEntity, DamageSource damageSource, float amount, boolean hasChanged) {
				
				if (casterEntity != null && !EffectsEntity.get(casterEntity).isAffectedBy(this)) {
					casterEntity.attackEntityFrom(damageSource, amount);
					return 0.0F;
				}
				
				return amount;
			}
			
		});
		
		
	}

}
