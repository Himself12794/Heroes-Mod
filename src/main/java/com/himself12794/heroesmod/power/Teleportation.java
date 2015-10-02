package com.himself12794.heroesmod.power;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

public class Teleportation extends PowerBuff {

	private static class State {
		private static final int REMEMBER = 0;
		private static final int RECALL = 1;
	}
	
	public Teleportation() {
		super("teleportation");
		//setPreparationTime(40);
		setCost(20 * 5);
		setMaxFunctionalState(1);
	}
	
	@Override
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {

		PowersEntity wrap = PowersEntity.get(caster);
		PowerProfile profile = wrap.getPowerProfile(this);
		
		if (state == State.REMEMBER) {
			
			profile.setBoolean("locationRemembered", true);
			profile.setInteger("dimension", caster.dimension);
			profile.setDouble("x", caster.posX);
			profile.setDouble("y", caster.posY);
			profile.setDouble("z", caster.posZ);
			
		} else if (state == State.RECALL) {
			
			if (profile.getBoolean("locationRemembered") && profile.getInteger("dimension") == caster.dimension) {
				
				double oldX = caster.posX;
				double oldY = caster.posY;
				double oldZ = caster.posZ;
				
				caster.setPositionAndUpdate(profile.getDouble("x"), profile.getDouble("y"), profile.getDouble("z"));
				((EntityPlayer)caster).preparePlayerToSpawn();
				profile.setDouble("x", oldX);
				profile.setDouble("y", oldY);
				profile.setDouble("z", oldZ);
				profile.setInteger("dimension", caster.dimension);
				
				return true;
			} else if (profile.getInteger("dimension") != caster.dimension) {
				if (world.isRemote) caster.addChatMessage( new ChatComponentText( "You can't teleport to a different dimension" ) );
			} else {
				if (world.isRemote) caster.addChatMessage( new ChatComponentText( "You haven't set a teleport location" ) );
			}
		}
		
		return false;
	}
	
	@Override
	public String getInfo(PowerProfile profile) {
		String text;
			
		if (profile.getBoolean("locationRemembered")) {			
			text = "(" + (int)profile.getDouble("x") + "," + (int)profile.getDouble("y") + "," + (int)profile.getDouble("z") + "), " + profile.getInteger("dimension"); 
		} else {
			text = "No teleportation location set";
		}
		
		return text;
	}
	
	public String getDisplayName(PowerProfile profile) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name" + profile.getState())).trim();
	}

}
