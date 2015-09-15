package com.himself12794.heroesmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
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
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, useMetadata = true)
public class HeroesMod {

	boolean init = false;

	@Instance(value = "powersAPI")
	private static PowersAPI instanceAPI;
	
	@Instance(value = Reference.MODID)
	private static HeroesMod instance;

	public static Logger logger;

	public static void print(Object msg) {
		logger.info(msg);
	}
	
	public static HeroesMod instance() {
		return instance;
	}
	
	public static PowersAPI instanceAPI() {
		return instanceAPI;
	}

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	private static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {

		logger = event.getModLog();
		proxy.preinit(event);
		init = true;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

	}
	
	@EventHandler 
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postinit(event);
	}

	public boolean isInitialized() {
		return init;
	}
	
	public static CommonProxy proxy() {
		return proxy;
	}
}