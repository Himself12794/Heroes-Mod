package com.himself12794.heroesmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import com.himself12794.powersapi.util.Reference;

public final class ModItems {
	
	public static final int NUMBER = ModItems.class.getDeclaredFields().length - 1;
	public static Item skillBook;
	
	public static void addItems() {
		
		skillBook = new SkillBook();
		
	}
	
	public static void registerTextures( FMLInitializationEvent event ) {
		if(event.getSide().isClient()) {
			
		    RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		    
		    renderItem.getItemModelMesher().register(skillBook, 0, new ModelResourceLocation(Reference.MODID + ":" + ((SkillBook) skillBook).getName(), "inventory"));
		    
		}
	}
}
