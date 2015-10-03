package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.himself12794.heroesmod.world.BioticExplosion;
import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.util.UsefulMethods;

public class Launch extends PowerBuff {

	public Launch() {
		super("launch");
	}
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		
		if(caster != null && !caster.isDead) {
			//caster.motionY = 8.0D;
			BioticExplosion.doExplosion(world, caster, 2.0F, 10.0F);
			if (caster instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)caster;
				if (player.capabilities.allowFlying) {
					player.capabilities.isFlying = true;
					player.setSprinting(true);
				}			
				
			}			
			Vec3 target = UsefulMethods.getMouseOverExtendedUniversal(caster, 100.0F).hitVec;
			Vec3 launchVector = target.subtract(caster.getPositionVector());
			launchVector = launchVector.normalize();
			
			caster.motionX = 8.0D * launchVector.xCoord;
			caster.motionY = 8.0D * launchVector.yCoord;
			caster.motionZ = 8.0D * launchVector.zCoord;
			return true;
		}
		
		return false;
		
	}

}
