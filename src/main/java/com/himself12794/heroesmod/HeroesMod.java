package com.himself12794.heroesmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

import com.himself12794.heroesmod.proxy.ClientProxy;
import com.himself12794.heroesmod.proxy.CommonProxy;
import com.himself12794.heroesmod.util.Reference;

/**
 * This mod is meant to emulate many of the different abilities found in the
 * heroes universe.
 * 
 * @author phwhitin
 *
 */
@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, dependencies = Reference.DEPENDENCIES)
public class HeroesMod {

	boolean init = false;

	@Instance(value = Reference.MODID)
	public static HeroesMod instance;

	public static Logger logger;

	public static void print(Object msg) {
		logger.info(msg);
	}

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;

	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {

		init = true;
		logger = event.getModLog();
		proxy.preinit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// MinecraftForge.EVENT_BUS.register(new EagleVision());
		proxy.init(event);

	}

	public boolean isInitialized() {
		return init;
	}
}