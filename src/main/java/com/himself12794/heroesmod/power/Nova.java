package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.world.BioticExplosion;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.storage.PowersWrapper;

public class Nova extends PowerBuff {
	
	public Nova() {
		setUnlocalizedName("nova");
		setCoolDown(0);
	}
	
	
	public boolean onPreparePower(World worldIn, EntityPlayer playerIn) {
		int cooldown = PowersWrapper.get(playerIn).getCooldownRemaining(Powers.CHARGE);
		return Powers.CHARGE.getCooldown() - cooldown < 20 || playerIn.capabilities.isCreativeMode ;
	}

	public boolean onCast(World world, EntityLivingBase caster, float modifier) {
		
		PowersWrapper.get(caster).getPowerEffectsData().addPowerEffect(PowerEffects.slam, 2, caster, this);

		return true;
	}
	
	@Override
	public boolean onFinishedCasting(World world, EntityLivingBase caster, MovingObjectPosition pos) {

		Explosion splodey = new BioticExplosion(world, caster, caster.posX, caster.posY, caster.posZ, 5.0F, false, true);
		splodey.doExplosionA();
		splodey.doExplosionB(true);
		
		return true;
	}

}
