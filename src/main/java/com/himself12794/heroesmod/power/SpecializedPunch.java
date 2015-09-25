package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.himself12794.heroesmod.AbilitySets;
import com.himself12794.heroesmod.storage.AbilitiesEntity;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.storage.PowerProfile;

public class SpecializedPunch extends PowerInstant {
	
	private final String NAME = "specializedPunch";
	
	public SpecializedPunch() {
		setRange(5);
		setPower(10.0F);
		setCost(60);
		setUnlocalizedName(NAME);
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {

		if (state == 0 && target.entityHit instanceof EntityLivingBase) {
			doHighVelocityPunch(caster, (EntityLivingBase) target.entityHit);
			return true;
		} else if (state == 1 && target.entityHit instanceof EntityLivingBase) {
			doLowVelocityPunch(caster, (EntityLivingBase) target.entityHit);
			return true;
		}
		
		return false;
	}
	
	public int getMaxFunctionalState(PowerProfile profile) { 
		int maxState = AbilitiesEntity.get(profile.theEntity).abilitySets.contains(AbilitySets.enhancedStrength) ? 1 : 0;
		return maxState; 
	}
	
	public void onStateChanged(World world, EntityLivingBase caster, int prevState, int currState) {
		
		/*if (currState == 0 && world.isRemote) {
			caster.addChatMessage( new ChatComponentText( "Changed to high velocity punch" ) );
		} else if (world.isRemote && currState == 1 ) {
			caster.addChatMessage( new ChatComponentText( "Changed to low velocity punch" ) );
		}*/
		
	}
	
	public String getDisplayName(PowerProfile profile) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name" + profile.getState())).trim();
	}
	
	private void doLowVelocityPunch(EntityLivingBase puncher, EntityLivingBase targetEntity) {
		
		if (targetEntity != null) {
			
			float j = 8.0F;
			
			targetEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) puncher), 5.0F);
			targetEntity.addVelocity((double)(-MathHelper.sin(puncher.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F), 0.1D, (double)(MathHelper.cos(puncher.rotationYaw * (float)Math.PI / 180.0F) * (float)j * 0.5F));
		}
			
	}
	
	private void doHighVelocityPunch(EntityLivingBase puncher, EntityLivingBase targetEntity) {
		if (targetEntity != null) {
			targetEntity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) puncher).setDamageBypassesArmor(), getPower(1.0F));
			targetEntity.addVelocity((double)(-MathHelper.sin(puncher.rotationYaw * (float)Math.PI / 180.0F) * (float)1.0F * 0.5F), 0.1D, (double)(MathHelper.cos(puncher.rotationYaw * (float)Math.PI / 180.0F) * (float)1.0F * 0.5F));
		}
		
	}

}
