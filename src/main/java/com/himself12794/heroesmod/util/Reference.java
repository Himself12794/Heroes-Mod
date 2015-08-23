package com.himself12794.heroesmod.util;

import com.himself12794.heroesmod.proxy.ClientProxy;

/**
 * Strings constants used throughout the mod.
 * 
 * @author phwhitin
 *
 */
public abstract class Reference {
	
    public static final String MODID = "HeroesMod";
    public static final String VERSION = "1.0";
    public static final String NAME = "Heroes Mod";
    public static final String DEPENDENCIES = "required-after:" + com.himself12794.powersapi.util.Reference.MODID;
    public static final String CLIENT_PROXY = "com.himself12794.heroesmod.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.himself12794.heroesmod.proxy.CommonProxy";
    
    /**
     * Tags used in NBT data.
     * 
     * @author Himself12794
     *
     */
    public static abstract class TagIdentifiers {
    	
    	public static final String power = MODID + ".power.currentPower";
    	public static final String powerCooldowns = MODID + ".power.powerCooldowns";
    	public static final String powerEffects = MODID + ".power.powerEffects";
    	
    }
    
    /**
     * References to sounds.
     * 
     * @author Philip
     *
     */
    public static abstract class Sounds {
    	
    	public static final String MAGICAL_EXPLOSION = MODID + ":magical_explosion";
    	public static final String FIREWORKS_LAUNCH = "fireworks.launch";
    	
    }
    
    
}