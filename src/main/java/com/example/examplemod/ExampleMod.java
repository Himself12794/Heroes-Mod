package com.example.examplemod;

import com.himself12794.powersapi.Spells;
import com.himself12794.powersapi.spell.Spell;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	Spell spell;
		// some example code
        System.out.println("DIRT BLOCK >> " + Spells.incinerate.getUnlocalizedName());
    }
}
