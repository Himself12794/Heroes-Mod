package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.power.PowerInstant;

public class PowersRegistraton {

	public static void registerPowers() {

		Power.registerPower(new PowerInstant().setUnlocalizedName("damage"));
		Power.registerPower(new PowerInstant().setUnlocalizedName("death").setPower(1000.0F).setCoolDown(178));
		Power.registerPower(new Incinerate());
		Power.registerPower(new Lightning());
		Power.registerPower(new Heal());
		Power.registerPower(new Dummy());
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
		Power.registerPower(new PowerEffectActivatorBuff("phasing", 100, 0, PowerEffects.phasing, 100));
		Power.registerPower(new Flare());
		Power.registerPower(new Eclipse());
		Power.registerPower(new PowerEffectActivatorBuff("break", 20 * 60, 0, PowerEffects.breakFx, 20 * 60 * 5) {

			public boolean isRemoveableByCaster(EntityLivingBase affected,
					EntityLivingBase caster, int timeRemaining) {
				return false;
			}

		});
		Power.registerPower(new BlockRecall());
		Power.registerPower(new BlockRemember());
		Power.registerPower(new Charge());
		Power.registerPower(new Nova());

	}
}
