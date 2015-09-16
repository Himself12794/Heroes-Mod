package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.PowersAPI;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.storage.PowerProfile;

public class PowersRegistraton {

	public static void registerPowers() {

		PowersAPI.registerPower(new Incinerate());
		PowersAPI.registerPower(new Lightning());
		PowersAPI.registerPower(new Heal());
		PowersAPI.registerPower(new PowerEffectActivatorBuff("immortalize", 0, 0, PowerEffects.rapidCellularRegeneration, -1) {
			
			@Override
			public boolean isRemoveableByCaster(EntityLivingBase affected,
					EntityLivingBase caster, int timeRemaining) {
				
				if (affected instanceof EntityPlayer) {
					return ((EntityPlayer)caster).capabilities.isCreativeMode;
				}
				return false;
			}
				
		});
		PowersAPI.registerPower(new Flames());
		PowersAPI.registerPower(new Slam());
		PowersAPI.registerPower(new Punt());
		PowersAPI.registerPower(new Telekinesis());
		PowersAPI.registerPower(new Flare());
		PowersAPI.registerPower(new Eclipse());
		PowersAPI.registerPower(new BlockMemory());
		PowersAPI.registerPower(new Charge());
		PowersAPI.registerPower(new Nova());
		PowersAPI.registerPower(new Launch());
		PowersAPI.registerPower(new SpecializedPunch());
		PowersAPI.registerPower(new PowerEffectActivatorBuff("speedBoost", 20 * 10, 0, PowerEffects.speedBoost, 20 * 30, 3){
			
			@Override
			public int getCooldown(PowerProfile profile) {
				return profile.level == 1 ? 20 * 30 : (profile.level == 2 ? 20 * 15 : (profile.level == 3 ? 20 * 5 : 20 * 30));
			}
			
			@Override 
			public int getEffectDuration(PowerProfile profile) {
				return profile.level == 1 ? 20 * 30 : (profile.level == 2 ? 20 * 45 : (profile.level == 3 ? 20 * 60 : 20 * 30));
			}
			
		});
		PowersAPI.registerPower(new PowerBuff("enderAccess", 0) {
			
			public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
				
				if (caster instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)caster;
					player.displayGUIChest(player.getInventoryEnderChest());
					Powers.BLOCK_MEMORY.playSound(world, player.getPosition(), true);
					return true;
				}
				
				return false;
			}
			
			@Override
			public String getInfo(PowerProfile profile) {
				
				/*if (profile.theEntity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)profile.theEntity;
					
					InventoryEnderChest enderInv = player.getInventoryEnderChest();
					int totalSlots = enderInv.getSizeInventory();
					int filledSlots = 0;
					
					for (int i = 0; i < totalSlots; i++) {
						if (enderInv.getStackInSlot(i) != null) filledSlots++;
					}
					
					//return "Ender Storage Space: " + filledSlots + "/" + totalSlots;					
				}*/
				
				return null;
			}
			
		});

	}
}
