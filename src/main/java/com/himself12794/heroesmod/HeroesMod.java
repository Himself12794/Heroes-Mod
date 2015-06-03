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

/**
 * This mod is a Powers API. It was whatever I shoved into it, but it's become steady at this.
 * 
 * @author Himself12794
 *
 */
@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, dependencies = "required-after:powersAPI")
public class HeroesMod {    

	@Instance(value = Reference.MODID)
	public static HeroesMod instance;
	
	public static Logger logger;
	public static void print(Object msg) {
		logger.info(msg);
	}
	
	public static CreativeTabs heroesMod = new CreativeTabs("Heroes Mod") {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getTabIconItem() {
	        return Items.enchanted_book;
	    }
	};
	

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