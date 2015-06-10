package com.himself12794.heroesmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Logger;

import com.himself12794.heroesmod.proxy.CommonProxy;
import com.himself12794.heroesmod.util.Reference;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, dependencies = "required-after:powersAPI")
public class HeroesMod {    

	@Instance(value = Reference.MODID)
	public static HeroesMod instance;
	
	public static Logger logger;
	public static void print(Object msg) {
		logger.info(msg);
	}	

	@SidedProxy(
			clientSide="com.himself12794.heroesmod.proxy.ClientProxy", 
			serverSide="com.himself12794.heroesmod.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	
	
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    	
    	logger = event.getModLog();
    	proxy.preinit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	
    	//MinecraftForge.EVENT_BUS.register(new EagleVision());
    	proxy.init(event);
        
    }
}