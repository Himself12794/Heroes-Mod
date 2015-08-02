package com.himself12794.heroesmod.util;

import com.himself12794.heroesmod.proxy.ClientProxy;

/**
 * Strings constants used throughout the mod.
 * 
 * @author phwhitin
 *
 */
public class Reference {
	
    public static final String MODID = "HeroesMod";
    public static final String VERSION = "1.0";
    public static final String NAME = "Heroes Mod";
    public static final String DEPENDENCIES = "required-after:" + com.himself12794.powersapi.util.Reference.MODID;
    public static final String CLIENT_PROXY = "com.himself12794.heroesmod.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.himself12794.heroesmod.proxy.CommonProxy";
    
    public static class TagIdentifiers {
    	
    	public static final String power = MODID + ".power.currentPower";
    	public static final String powerCooldowns = MODID + ".power.powerCooldowns";
    	public static final String powerEffects = MODID + ".power.powerEffects";
    	
    }
    
    
}