package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.storage.PowerProfile;

public class PowersRegistraton {

	public static void registerPowers() {

		//Power.registerPower(new PowerInstant().setUnlocalizedName("damage"));
		//Power.registerPower(new PowerInstant().setUnlocalizedName("death").setPower(1000.0F).setCoolDown(178));
		Power.registerPower(new Incinerate());
		Power.registerPower(new Lightning());
		Power.registerPower(new Heal());
		//Power.registerPower(new Dummy());
		Power.registerPower(new PowerEffectActivatorBuff("immortalize", 0, 0, PowerEffects.rapidCellularRegeneration, -1) {
			
			@Override
			public boolean isRemoveableByCaster(EntityLivingBase affected,
					EntityLivingBase caster, int timeRemaining) {
				
				if (affected instanceof EntityPlayer) {
					return ((EntityPlayer)caster).capabilities.isCreativeMode;
				}
				return false;
			}
				
		});
		Power.registerPower(new Flames());
		Power.registerPower(new Slam());
		Power.registerPower(new Punt());
		Power.registerPower(new Telekinesis());
		//Power.registerPower(new PowerEffectActivatorBuff("phasing", 100, 0, PowerEffects.phasing, 100));
		Power.registerPower(new Flare());
		Power.registerPower(new Eclipse());
		Power.registerPower(new PowerEffectActivatorBuff("break", 20 * 60, 0, PowerEffects.breakFx, -1) {

			public boolean isRemoveableByCaster(EntityLivingBase affected,
					EntityLivingBase caster, int timeRemaining) {
				return false;
			}

		});
		Power.registerPower(new BlockMemory());
		Power.registerPower(new Charge());
		Power.registerPower(new Nova());
		Power.registerPower(new Launch());
		Power.registerPower(new SpecializedPunch());
		Power.registerPower(new PowerEffectActivatorBuff("speedBoost", 20 * 10, 0, PowerEffects.speedBoost, 20 * 30){
			
			{
				setMaxLevel(3);
				setUsesToLevelUp(50);
			}
			
			@Override
			public int getCooldown(PowerProfile profile) {
				return profile.level == 1 ? 20 * 30 : (profile.level == 2 ? 20 * 15 : (profile.level == 3 ? 20 * 5 : 20 * 30));
			}
			
			@Override 
			public int getEffectDuration(PowerProfile profile) {
				return profile.level == 1 ? 20 * 30 : (profile.level == 2 ? 20 * 45 : (profile.level == 3 ? 20 * 60 : 20 * 30));
			}
			
		});

	}
}
