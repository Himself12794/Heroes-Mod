package com.himself12794.heroesmod.util;


/**
 * Strings constants used throughout the mod.
 * 
 * @author Himself12794
 *
 */
public abstract class Reference {
	
    public static final String MODID = "HeroesMod";
    public static final String VERSION = "0.9-rev3";
    public static final String NAME = "Heroes Mod";
    public static final String MOD_AUTHOR = "Himself12794";
    public static final String CLIENT_PROXY = "com.himself12794.heroesmod.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.himself12794.heroesmod.proxy.CommonProxy";
    public static final String GUI_FACTORY = "com.himself12794.heroesmod.GuiFactory";
    
    /**
     * References to sounds.
     * 
     * @author Himself12794
     *
     */
    public static abstract class Sounds {
    	
    	public static final String MAGICAL_EXPLOSION = MODID + ":magical_explosion";
    	public static final String BIOTIC_EXPLOSION = MODID + ":biotic_explosion";
    	public static final String BANSHEE_SCREAM = MODID + ":banshee_scream";
    	public static final String SWOOSH = MODID + ":swoosh";
    	public static final String FIREWORKS_LAUNCH = "fireworks.launch";
    	
    }
    
    
}