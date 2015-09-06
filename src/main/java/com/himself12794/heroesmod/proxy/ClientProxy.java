package com.himself12794.heroesmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.himself12794.heroesmod.events.SoundHacking;
import com.himself12794.heroesmod.gui.PowerOverlayGui;
import com.himself12794.heroesmod.network.server.S01SpawnParticles;

public class ClientProxy extends CommonProxy {
	
    @Override
    public void preinit(FMLPreInitializationEvent event) {
    	super.preinit(event);
    }


    @Override
    public void init(FMLInitializationEvent event) {

    	super.init(event);
    	if (Loader.isModLoaded("powersAPI")) {
    		
    		MinecraftForge.EVENT_BUS.register(new SoundHacking());
    		MinecraftForge.EVENT_BUS.register(new PowerOverlayGui(Minecraft.getMinecraft()));
    		//ModItems.registerTextures(event);
    		
    	}
    	
    	
    }
    
    public Side getSide() {
    	return Side.CLIENT;
    }
    
    public EntityPlayer getPlayer() {
    	return Minecraft.getMinecraft().thePlayer;
    }
    
    
}

