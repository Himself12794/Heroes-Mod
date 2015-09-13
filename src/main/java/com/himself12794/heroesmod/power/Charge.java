package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.storage.PowersWrapper;

public class Charge extends PowerInstant {

	public Charge() {
		setUnlocalizedName("charge");
		setMaxConcentrationTime(0);
		setRange(40);
		setCoolown(60);
		setPower(10.0F);
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		if (target.entityHit instanceof EntityLivingBase || target.entityHit instanceof EntityDragonPart) {
			Vec3 move = target.hitVec;
			
			PowersWrapper.get(caster).getPowerEffectsData().addPowerEffect(PowerEffects.immortality, 10, caster, this);
			caster.motionY = 0.25D;world.playSoundAtEntity(caster, Sounds.BIOTIC_EXPLOSION, 1.5F, 2.0F);
			caster.setPosition(move.xCoord , move.yCoord, move.zCoord);
			caster.motionY = 0.25D;world.playSoundAtEntity(caster, Sounds.BIOTIC_EXPLOSION, 1.5F, 2.0F);
			
			float j = 5.0F;
			target.entityHit.addVelocity((double)(-MathHelper.sin(caster.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.cos(caster.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F));
			
			
			//move = caster.getLookVec();			
			//target.entityHit.motionX = move.xCoord * 6.0D;
			//target.entityHit.motionY = move.yCoord * 8.0D;
			//target.entityHit.motionZ = move.zCoord * 6.0D;
			((EntityLivingBase)target.entityHit).attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) caster), getPower(modifier));

			
			return true;
		}
		return false;
		
	}

}
