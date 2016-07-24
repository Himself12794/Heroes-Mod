package com.himself12794.heroesmod;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.himself12794.heroesmod.handlers.WorldHealingHandler;
import com.himself12794.heroesmod.proxy.CommonProxy;
import com.himself12794.heroesmod.util.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This mod is meant to emulate many of the different abilities found in the
 * heroes universe.
 * 
 * @author phwhitin
 *
 */
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, useMetadata = true, guiFactory = Reference.GUI_FACTORY)
public class HeroesMod {
	
	@Mod.Instance(Reference.MODID)
	private static HeroesMod instance;
	
	@Mod.Metadata(Reference.MODID)
	private static ModMetadata meta;

	private static Logger logger;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	private static CommonProxy proxy;
	
	public final ModConfig config = new ModConfig(new File("config/" + Reference.MODID + ".cfg"));
	public final WorldHealingHandler worldHealingHandler = new WorldHealingHandler();
	
	boolean preInit;

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {

		configureMetadata(meta);
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
		return getMod().config;
	}
	
	public static HeroesMod getMod() {
		return instance;
	}
	
	public static CommonProxy proxy() {
		return proxy;
	}
	
	public static Logger logger() {
		return logger;
	}
}