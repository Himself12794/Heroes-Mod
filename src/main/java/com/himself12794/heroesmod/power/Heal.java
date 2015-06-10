package com.himself12794.heroesmod.power;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.power.PowerBuff;
import com.himself12794.heroesmod.AbilitySets;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.gui.PowersGUI;
import com.himself12794.heroesmod.powerfx.PowerEffects;

public class Heal extends PowerBuff {
	
	public Heal() {
		setDuration(0);
		setPower(0.25F);
		setCoolDown(1);
		//setType(SpellType.BUFF);
		setUnlocalizedName("heal");
	} 
	
	public boolean onCast(World world, EntityLivingBase caster, ItemStack stack, float modifier) {
		boolean flag = false;
		if (caster.getHealth() < caster.getMaxHealth()) {
			flag = true;
			caster.heal(getPower() * modifier);
		}
		
		if (HeroesMod.proxy.getSide().isClient() && caster instanceof EntityPlayer) {
			
			//Minecraft.getMinecraft().displayGuiScreen(new PowersGUI((EntityPlayer) caster));
			try {
				stack.getItem().getFontRenderer(stack).drawString(AbilitySets.pyrokinesis.getUnlocalizedName(), 87, 16, 4210752);
			} catch (Exception e) {
				System.out.println(AbilitySets.pyrokinesis.getUnlocalizedName());
			}
			
		}
		
		return flag;
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caste, float modifier ) {
		boolean flag = false;
		EntityLivingBase entity = null;
		if (target.entityHit instanceof EntityLivingBase)
			 entity = ((EntityLivingBase)target.entityHit);
			if(entity.getHealth() < entity.getMaxHealth()) {
				flag = true;
				entity.heal(getPower() * modifier);
			}
		
		return flag;
	}
	
	public String getInfo() {
		return "Heals caster";
	}	
	
}
