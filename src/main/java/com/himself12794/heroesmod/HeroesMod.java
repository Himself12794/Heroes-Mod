package com.himself12794.heroesmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.himself12794.heroesmod.proxy.CommonProxy;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.PowersAPI;

/**
 * This mod is meant to emulate many of the different abilities found in the
 * heroes universe.
 * 
 * @author phwhitin
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, useMetadata = true, guiFactory = Reference.GUI_FACTORY)
public class HeroesMod {

	@Mod.Instance("powersAPI")
	private static PowersAPI instanceAPI;
	
	@Mod.Instance(Reference.MODID)
	private static HeroesMod instance;
	
	@Mod.Metadata(Reference.MODID)
	private static ModMetadata meta;

	private static Logger logger;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	private static CommonProxy proxy;
	
	private ModConfig config;
	boolean preInit;

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {

		configureMetadata(meta);
		config = new ModConfig(event);
		logger = event.getModLog();
		proxy.preinit(event);
		preInit = true;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

	}
	
	@Mod.EventHandler 
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}

	public boolean isInitialized() {
		return preInit;
	}
    
    private void configureMetadata(ModMetadata meta) {

		meta.name = Reference.NAME;
		meta.modId = Reference.MODID;
		meta.version = Reference.VERSION;
    }
	
	public static ModConfig config() {
		return instance().config;
	}
	
	public static HeroesMod instance() {
		return instance;
	}
	
	public static PowersAPI apiInstance() {
		return instanceAPI;
	}
	
	public static CommonProxy proxy() {
		return proxy;
	}
	
	public static Logger logger() {
		return logger;
	}
}