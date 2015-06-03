package com.himself12794.heroesmod;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.himself12794.heroesmod.item.ModItems;

public class ModRecipes {
	public final static int NUMBER = 1;
	public static void addRecipes() {
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powerActivator), Items.book);
		
	}
}
