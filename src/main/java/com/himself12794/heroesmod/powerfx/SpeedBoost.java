package com.himself12794.heroesmod.powerfx;

import java.lang.reflect.Field;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.Powers;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.ReflectUtils;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

/**
 * A variety of cool effects. At level 1, just speed. At level 2, the ability 
 * 
 * @author Himself12794
 *
 */
public class SpeedBoost extends PowerEffect {

	public SpeedBoost() {
		super("speedBoost", true, EffectType.BENEFICIAL);
	}
	
	public boolean onUpdate(EntityLivingBase entity, int timeLeft, EntityLivingBase caster, Power power) {

		if (!(entity instanceof EntityPlayer)) return false;
		
		PowerProfile profile = PowersEntity.get(entity).getPowerProfile(power);
		int level = profile != null  && power == Powers.speedBoost ? (profile.level < 3 ? profile.level : 3) : 1;

		double f1 = entity.motionX;
		double f2 = entity.motionZ;
		double f3 = MathHelper.sqrt_double(f1 * f1 + f2 * f2);
		
		if (f3 > 0.45) entity.extinguish();
				
		if (entity.moveForward > 1.0 && entity.getActivePotionEffect(Potion.moveSlowdown) == null) entity.setSprinting(true);
		
		if (level >= 2) doRunOnWater(entity, timeLeft);
		
		if (level >= 3) {

			ReflectUtils.setField(PlayerCapabilities.class, ((EntityPlayer)entity).capabilities, "speedOnGround", 0.5F);
			
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
		ReflectUtils.setField(PlayerCapabilities.class, ((EntityPlayer)entity).capabilities, "speedOnGround", 0.1F);
		ReflectUtils.setField(PlayerCapabilities.class, ((EntityPlayer)entity).capabilities, "flySpeed", 0.05F);
		ReflectUtils.setField(EntityPlayer.class, entity, "speedInAir", 0.02F);
	}
	
	private void doRunOnWater(EntityLivingBase entity, int timeLeft) {
		
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			float speed = entity.moveForward;
			
			BlockPos pos = player.getPosition();
			if (player.worldObj.isAirBlock(pos) && player.worldObj.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && speed > 0.5) {
				
				String sound = entity.worldObj.getBlockState(pos.down()).getBlock().getMaterial() == Material.lava ? "liquid.lavapop" : "game.player.swim.splash" ;
				
				HeroesNetwork.client().spawnParticles(EnumParticleTypes.WATER_BUBBLE, pos.getX(), pos.getY() - 0.5, pos.getZ(), 1.0F, 50, EnumRandomType.GAUSSIAN, null);
				if (timeLeft % 7 == 0) player.playSound(sound, 0.5F, 0.75F);
				player.motionY = 0.0;
				ReflectUtils.setField(PlayerCapabilities.class, ((EntityPlayer)entity).capabilities, "flySpeed", 0.5F);
				ReflectUtils.setField(EntityPlayer.class, player, "speedInAir", 0.5F);
				
			} else if (entity.isAirBorne && ((EntityPlayer)entity).capabilities.isFlying){
				ReflectUtils.setField(EntityPlayer.class, player, "speedInAir", 0.02F);
			}
		}
		
	}
	
}
