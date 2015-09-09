package com.himself12794.heroesmod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersWrapper;

public class PowerOverlayGui extends Gui {
	
	private Minecraft mc;
	private RenderItem itemRender;
	private FontRenderer fontRendererObj;
	
	private static final int BUFF_ICON_SIZE = 18;
	private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; 
	private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	private static final int BUFF_ICONS_PER_ROW = 8;
	

	public PowerOverlayGui(Minecraft mc) {
		super();
		this.mc = mc;
		this.itemRender = mc.getRenderItem();
		this.fontRendererObj = mc.fontRendererObj;
	}

	//
	// This event is called by GuiIngameForge during each frame by
	// GuiIngameForge.pre() and GuiIngameForce.post().
	//
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event) {

		// Starting position for the buff bar - 2 pixels from the top left
		// corner.
	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE) {      
	      return;
	    }
	    
		int xPos = 2;
		int yPos = 2;
		
		PowersWrapper wrapper = PowersWrapper.get(Minecraft.getMinecraft().thePlayer);
		PowerProfile powerPrimary = wrapper.getPowerProfile(wrapper.getPrimaryPower());
		PowerProfile powerSecondary = wrapper.getPowerProfile(wrapper.getSecondaryPower());

		if (powerPrimary != null) { 
			drawName(powerPrimary, xPos, yPos);
		}
		yPos += 40;
		if (powerSecondary != null) { 
			drawName(powerSecondary, xPos, yPos);
		}
	}
	
	private void drawName(PowerProfile profile, int x, int yPos) {

		int color = profile.cooldownRemaining <= 0 ? 128 : 64;  
		
		GlStateManager.pushMatrix();
		//GL11.glColor3b((byte)255, (byte)0, (byte)0);
		drawString(fontRendererObj, profile.thePower.getDisplayName(), x, yPos, color);
		if (profile.cooldownRemaining > 0) 
			drawString(fontRendererObj, "Cooldown Remaining: " + String.format("%.1f", profile.cooldownRemaining / 20.0F), x, yPos + 16, color );
		GlStateManager.popMatrix();
	}
	
	private void renderPower(PowerProfile profile, int x, int y) {
		
		GlStateManager.pushMatrix();
		//drawString(fontRendererObj, profile.thePower.getDisplayName(), x, y, 64);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);      
		mc.renderEngine.bindTexture(profile.thePower.getIcon(profile));
		GL11.glScaled(0.065, 0.065, -185.0D);
		drawTexturedModalRect(x, y, 2, 2, 256, 256);
		GlStateManager.popMatrix();
		
	}
	
    /**
     * Render an ItemStack. Args : stack, x, y, format
     */
    private void drawItemStack(ItemStack stack, int x, int y, String altText) {
    	GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = -185.0F;
        this.itemRender.zLevel = -185.0F;
        FontRenderer font = null;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}
