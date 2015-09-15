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
		yPos += 56;
		if (powerSecondary != null) { 
			drawData(powerSecondary, xPos, yPos);
		}
	}
	
	private void drawData(PowerProfile profile, int x, int yPos) {

		int color = profile.cooldownRemaining <= 0 ? 127 : 63;  
		
		GlStateManager.pushMatrix();
		drawString(fontRendererObj, profile.thePower.getDisplayName(profile) + " (Lv " + profile.level + ")", x, yPos, color);
		yPos += 8;
		
		String info = profile.thePower.getInfo(profile);
		if (info != null) {
			drawString(fontRendererObj, info, x, yPos, color);
			yPos += 8;
		}
		
		drawString(fontRendererObj, "Cooldown: " + String.format("%.1f", profile.thePower.getCooldown(profile) / 20.0F) + "s", x, yPos, color );
		yPos += 8;
		
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
		
		if (profile.cooldownRemaining > 0) { 
			drawString(fontRendererObj, "Cooldown Remaining: " + String.format("%.1f", profile.cooldownRemaining / 20.0F), x, yPos, color );
			yPos += 8;
		}
		
		GlStateManager.popMatrix();
	}
}
