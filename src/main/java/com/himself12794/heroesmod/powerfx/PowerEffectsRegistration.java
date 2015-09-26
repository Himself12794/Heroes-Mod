package com.himself12794.heroesmod.powerfx;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.HeroesMod;
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
		PowerEffect.registerEffect(new Telekinesis());
		PowerEffect.registerEffect(new PowerEffect("break", true, EffectType.BENEFICIAL));
		PowerEffect.registerEffect(new PowerEffect("telekineticShield", true, EffectType.BENEFICIAL, true));
		PowerEffect.registerEffect(new PowerEffect("immortality", false, EffectType.TAG, true));
		PowerEffect.registerEffect(new EmphaticMimicry());
		PowerEffect.registerEffect(new SpeedBoost());
		PowerEffect.registerEffect(new PowerEffect("karma", true, EffectType.MALICIOUS) {
			
			{
				setRequiresCaster();
			}
			
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
