package com.himself12794.heroesmod;

import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.google.common.collect.Lists;
import com.himself12794.heroesmod.util.Reference;


public class GuiFactory implements IModGuiFactory {
	
	private Minecraft mc;

	@Override
	public void initialize(Minecraft minecraftInstance) { 
		mc = minecraftInstance;
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return ModConfigGUI.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}
	
	public static class ModConfigGUI extends GuiConfig {
		
		private static final List elements = Lists.newArrayList(new ConfigElement(HeroesMod.config().generalModConfig), new ConfigElement(HeroesMod.config().abilities));
		
		public ModConfigGUI(GuiScreen parent) {
			super(parent, elements, Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(HeroesMod.config().mainConfig.toString()));
		}
	}

}
