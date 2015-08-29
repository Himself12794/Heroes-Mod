package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.heroesmod.world.MagicalExplosion;
import com.himself12794.powersapi.entity.EntityPower;
import com.himself12794.powersapi.power.PowerRanged;

public class ExplodingBolt extends PowerRanged {

	public static final String NAME = "explodingBolt";

	public ExplodingBolt() {
		setPower(40.0F);
		setCoolDown(200);
		setUnlocalizedName(NAME);
		shouldRender = true;
	}
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier) {
		caster.playSound(Sounds.FIREWORKS_LAUNCH, 2.0F, 1.0F);
		return true;
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		// world.newExplosion(caster, target.hitVec.xCoord,
		// target.hitVec.yCoord, target.hitVec.zCoord, 3.0F, false, false);
		MagicalExplosion magicalExplosion = new MagicalExplosion(world, caster,
				target.hitVec.xCoord, target.hitVec.yCoord,
				target.hitVec.zCoord, 5.0F, false, true);
		
		magicalExplosion.setLimit(getPower());
		
		magicalExplosion.doExplosionA();
		magicalExplosion.doExplosionB(true);

		return true;
	}

	@Override
	public void onUpdate(EntityPower spell) {

		World world = spell.worldObj;
		
		if (spell.getThrower().getDistanceToEntity(spell) > 2.0F) {
			

			
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
