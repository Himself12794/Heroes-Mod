package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.world.BioticExplosion;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.storage.EffectsEntity;
import com.himself12794.powersapi.storage.PowersEntity;

public class Nova extends PowerBuff {
	
	public Nova() {
		super("nova");
	}

	@Override
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		
		EffectsEntity.get(caster).addPowerEffect(PowerEffects.slam, 3, caster, this);
		
		caster.setSprinting(true);

		return true;
	}
	
	@Override
	public boolean onFinishedCasting(World world, EntityLivingBase caster, MovingObjectPosition pos, int state) {
		BioticExplosion.doExplosion(world, caster, 5.0F, getPower(PowersEntity.get(caster).getPowerProfile(this).useModifier));
		return true;
	}

}
