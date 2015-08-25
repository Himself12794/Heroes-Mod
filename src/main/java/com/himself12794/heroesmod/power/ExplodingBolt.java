package com.himself12794.heroesmod.power;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.heroesmod.world.MagicalExplosion;
import com.himself12794.powersapi.PowersAPI;
import com.himself12794.powersapi.entity.EntitySpell;
import com.himself12794.powersapi.power.PowerRanged;

public class ExplodingBolt extends PowerRanged {

	public static final String NAME = "explodingBolt";

	public ExplodingBolt() {
		setPower(40.0F);
		setCoolDown(200);
		setUnlocalizedName(NAME);
		shouldRender = true;
	}
	
	public boolean onCast(World world, EntityLivingBase caster, ItemStack stack, float modifier) {
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
	public void onUpdate(EntitySpell spell) {

		World world = spell.worldObj;

		for (int i = 0; i < 10; i++) {
			spell.worldObj.spawnParticle(EnumParticleTypes.SPELL, spell.posX
					+ world.rand.nextGaussian() * 0.25F, spell.posY
					+ world.rand.nextGaussian() * 0.25F, spell.posZ
					+ world.rand.nextGaussian() * 0.25F, 0, 0, 0);
		}
	}

	@Override
	public float getBrightness() {
		return 15.0F;
	}

}
