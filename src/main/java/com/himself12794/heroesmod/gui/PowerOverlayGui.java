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

import com.himself12794.powersapi.PowersAPI;
import com.himself12794.powersapi.item.ModItems;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.util.DataWrapper;

//
// GuiBuffBar implements a simple status bar at the top of the screen which
// shows the current buffs/debuffs applied to the character.
//
// TODO add effects view, fix cooldown
public class PowerOverlayGui extends Gui {
	
	private Minecraft mc;
	private RenderItem itemRender;
	private FontRenderer fontRendererObj;
	private ItemStack primaryPower;
	private ItemStack secondaryPower;
	
	private static final int BUFF_ICON_SIZE = 18;
	private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; 
	private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	private static final int BUFF_ICONS_PER_ROW = 8;
	

	public PowerOverlayGui(Minecraft mc) {
		super();
		this.itemRender = mc.getRenderItem();
		this.fontRendererObj = mc.fontRendererObj;
		this.primaryPower = new ItemStack(ModItems.powerActivator);
		this.secondaryPower = new ItemStack(ModItems.powerActivator);
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
		
		DataWrapper wrapper = DataWrapper.get(PowersAPI.proxy.getPlayer());
		Power powerPrimary = wrapper.getPrimaryPower();
		Power powerSecondary = wrapper.getSecondaryPower();
		
		if (powerPrimary != null) {
			powerPrimary.setPower(primaryPower);
			drawItemStack(primaryPower, xPos, yPos, null);
			xPos += BUFF_ICON_SPACING;
		}
		
		if (powerSecondary != null) { 
			powerSecondary.setPower(secondaryPower);
			drawItemStack(secondaryPower, xPos, yPos, null);
		}

		
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
        //if (stack != null) font = stack.getItem().getFontRenderer(stack);
        //if (font == null) font = fontRendererObj;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        // TODO fix text bluring
        //this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, altText);
        
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}
