package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.Reference.Sounds;
import com.himself12794.powersapi.power.PowerInstant;

public class Eclipse extends PowerInstant {
	
	private static final String NAME = "eclipse";
	
	public Eclipse() {
		setPower(0);
		setCoolown(40);
		setUnlocalizedName(NAME);
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		if (!(target.entityHit instanceof EntityLivingBase)) return false;
		
		EntityLivingBase targetHit = (EntityLivingBase) target.entityHit;
		float health = targetHit.getHealth();
		
		targetHit.attackEntityFrom(DamageSource.magic, 0.75F * health);
		targetHit.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 100));
		targetHit.playSound(Sounds.SWOOSH, 3.0F, 1.0F);
		
		return true;
	}

}
