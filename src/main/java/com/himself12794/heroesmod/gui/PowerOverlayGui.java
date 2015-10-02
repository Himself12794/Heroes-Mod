package com.himself12794.heroesmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.storage.EffectsEntity;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

public class PowerOverlayGui extends Gui {
	
	private Minecraft mc;
	private RenderItem itemRender;
	private FontRenderer fontRendererObj;

	public PowerOverlayGui(Minecraft mc) {
		super();
		this.mc = mc;
		this.itemRender = mc.getRenderItem();
		this.fontRendererObj = mc.fontRendererObj;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {
		
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE) return;
	    
		int xPos = 2;
		int yPos = 2;
		
		PowersEntity wrapper = PowersEntity.get(mc.thePlayer);
		PowerProfile powerPrimary = wrapper.getPowerProfile(wrapper.getPrimaryPower());
		PowerProfile powerSecondary = wrapper.getPowerProfile(wrapper.getSecondaryPower());

		if (powerPrimary != null) { 
			drawData(powerPrimary, xPos, yPos);
		}
		yPos += 64;
		if (powerSecondary != null) { 
			drawData(powerSecondary, xPos, yPos);
		}
	}
	
	private void drawData(PowerProfile profile, int x, int yPos) {

		PowersEntity wrapper = PowersEntity.get(Minecraft.getMinecraft().thePlayer);
		
		int color;  
		
		if (wrapper.getPrimaryPower() == profile.thePower) {
			color = profile.cooldownRemaining <= 0 ? 0xAAFFFFFF : 0x80808080;
		} else {
			color = profile.cooldownRemaining <= 0 ? 0xAA4A9288 : 0x802D5C55;
		}
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		
		// Name and level
		String levelIdentifier = profile.getMaxLevel() > 1 ? " (Lv " + profile.level + ")" : "";
		drawString(fontRendererObj, profile.getDisplayName() + levelIdentifier, x, yPos, color);
		yPos += 8;
		
		// Optional info line
		String info = profile.getInfo();
		if (info != null) {
			drawString(fontRendererObj, info, x, yPos, color);
			yPos += 8;
		}
		
		// Cooldown time line
		int cooldown = profile.getCost();
		String cooldownText = cooldown > 0 ? String.format("%.1f", cooldown / 20.0F) + "s" : "None";
		drawString(fontRendererObj, "Cooldown Time: " + cooldownText, x, yPos, color );
		yPos += 8;
		
		// Use time left (if applicable)
		int useTimeLeft = wrapper.isPowerInUse(profile.thePower) ? wrapper.getPowerInUseTimeLeft(profile.thePower) : 0;
		if (useTimeLeft > 0) {
			drawString(fontRendererObj, "Use Time Left: " + String.format("%.1f", useTimeLeft / 20.0F) + "s", x, yPos, color );
			yPos += 8;
		}
		
		// Use time left (if applicable)
		int preparationTimeLeft = wrapper.isPowerInUse(profile.thePower) ? wrapper.getPreparationTimeLeft(profile.thePower) : 0;
		if (preparationTimeLeft > 0) {
			drawString(fontRendererObj, "Preparation Time Left: " + String.format("%.1f", preparationTimeLeft / 20.0F) + "s", x, yPos, color );
			yPos += 8;
		}
		
		// Additional information for buffs
		if (profile.thePower instanceof PowerEffectActivatorBuff) {
			PowerEffectActivatorBuff buff = (PowerEffectActivatorBuff)profile.thePower;
			
			String word = buff.getEffectDuration(profile) < 0 ? "Until Removed" : String.format("%.1f", buff.getEffectDuration(profile) / 20.0F) + "s"; 
			
			drawString(fontRendererObj, "Duration: " + word, x, yPos, color );
			yPos += 8;
			
			int timeLeft = EffectsEntity.get(profile.theEntity).getTimeRemaining(buff.getPowerEffect());
			if (timeLeft > 0) { 
				drawString(fontRendererObj, "Time Left: " + String.format("%.1f", timeLeft / 20.0F) + "s", x, yPos, color );
				yPos += 8;
			}
		}
		
		// Cooldown remaining (if applicable)
		if (profile.cooldownRemaining > 0) { 
			drawString(fontRendererObj, "Cooldown Remaining: " + String.format("%.1f", profile.cooldownRemaining / 20.0F) + "s", x, yPos, color );
			yPos += 8;
		}
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}
}
