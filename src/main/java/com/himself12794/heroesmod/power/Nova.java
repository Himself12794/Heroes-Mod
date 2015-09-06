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
	
	
	public boolean canCastPower(World worldIn, EntityPlayer playerIn, int state) {
		int cooldown = PowersWrapper.get(playerIn).getCooldownRemaining(Powers.CHARGE);
		return Powers.CHARGE.getCooldown(null) - cooldown < 5 || playerIn.capabilities.isCreativeMode ;
	}

	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		
		PowersWrapper.get(caster).getPowerEffectsData().addPowerEffect(PowerEffects.slam, 2, caster, this);

		return true;
	}
	
	@Override
	public boolean onFinishedCasting(World world, EntityLivingBase caster, MovingObjectPosition pos, int state) {

		Explosion splodey = new BioticExplosion(world, caster, caster.posX, caster.posY, caster.posZ, 5.0F, false, true);
		splodey.doExplosionA();
		splodey.doExplosionB(true);
		
		return true;
	}

}
