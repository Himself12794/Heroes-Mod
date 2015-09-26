package com.himself12794.heroesmod.powerfx;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

public class SpeedBoost extends PowerEffect {

	public SpeedBoost() {
		super("speedBoost", true, EffectType.BENEFICIAL);
	}
	
	public boolean onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster, Power power) {

		PowerProfile profile = PowersEntity.get(entity).getPowerProfile(power);
		int level = profile != null  && power == Powers.speedBoost ? 
				(profile.level < 3 ? profile.level : 3) : 1;
			
		if (entity.moveForward > 1.0 && entity.getActivePotionEffect(Potion.moveSlowdown) == null) entity.setSprinting(true);
		try {
			if (level >= 2) doRunOnWater(entity, timeLeft);
		} catch (Exception e) {
			HeroesMod.logger().error("An error occured in making the entity run over water");
		}
		
		if (level >= 3) {
			Vec3 look = entity.getLookVec();
			Vec3 pos = entity.getPositionVector();
			pos = pos.add(entity.getLookVec());
			BlockPos block = new BlockPos(pos.xCoord, pos.yCoord, pos.zCoord);
			entity.stepHeight = UtilMethods.getDistanceToNextOccupiableSpace(entity.worldObj, block);
		}
		
		entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, level, false, false));
		return true;
	}
	
	public void onRemoval(final EntityLivingBase entity, final EntityLivingBase caster, final Power power){
		entity.stepHeight = 0.0F;
	}
	
	private void doRunOnWater(EntityLivingBase entity, int timeLeft) throws Exception {
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float speed = entity.moveForward;
			
			Field speedInAir = EntityPlayer.class.getDeclaredField("speedInAir");
			speedInAir.setAccessible(true);
			
			BlockPos pos = player.getPosition();
			if (player.worldObj.isAirBlock(pos) && player.worldObj.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && speed > 0.5) {
				
				
				HeroesNetwork.client().spawnParticles(EnumParticleTypes.WATER_BUBBLE, pos.getX(), pos.getY() - 0.5, pos.getZ(), 1.0F, 50, EnumRandomType.GAUSSIAN, null);
				if (timeLeft % 7 == 0) player.playSound("game.player.swim.splash", 0.5F, 0.75F);
				player.motionY = 0.0;
				speedInAir.set(player, 0.05F);
				
			} else {
				speedInAir.set(player, 0.02F);
			}
		}
		
	}
	
}
