package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.heroesmod.world.BioticExplosion;
import com.himself12794.powersapi.entity.EntityPower;
import com.himself12794.powersapi.power.PowerRanged;

public class Flare extends PowerRanged {

	public static final String NAME = "flare";

	public Flare() {
		setPower(40.0F);
		setCost(200);
		setUnlocalizedName(NAME);
		shouldRender = true;
	}
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		caster.playSound(Sounds.FIREWORKS_LAUNCH, 2.0F, 1.0F);
		return true;
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier, int state) {
		
		Vec3 hitVec = target.hitVec;
		BioticExplosion.doExplosion(world, caster, hitVec.xCoord, hitVec.yCoord, hitVec.zCoord, 5.0F * modifier, getPower(modifier));

		return true;
	}

	@Override
	public void onUpdate(EntityPower spell) {

		if (spell.getThrower().getDistanceToEntity(spell) > 100.0F) spell.setDead();
		
		if (spell.getThrower().getDistanceToEntity(spell) > 2.0F) {
			
			spell.motionX *= 1.5;
			spell.motionY *= 1.5;
			spell.motionZ *= 1.5;
			
			double x = spell.motionX;
			double y = spell.motionY;
			double z = spell.motionZ;
			
			float norm = MathHelper.sqrt_double(x * x + y * y + z * z);
			
			x /= norm;
			y /= norm;
			z /= norm;			
			
			int f1 = 10;
			double f2 = 1.0D / f1;
			
			for (int i = 0; i < norm * f1; i++) {
				spell.worldObj.spawnParticle(EnumParticleTypes.SPELL, 
						spell.posX + x * i * f2, 
						spell.posY + y * i * f2,
						spell.posZ + z * i * f2, 
						0, 0, 0);
			}
		}
	}
	
	@Override
	public float getBrightness() {
		return 15.0F;
	}

}
