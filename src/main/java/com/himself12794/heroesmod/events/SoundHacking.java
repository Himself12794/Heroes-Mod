package com.himself12794.heroesmod.events;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.util.Reference.Sounds;

public class SoundHacking {

	
	@SubscribeEvent
	public void bansheeScream(PlaySoundEvent event) {
		
		if (event.name.equals("mob.endermen.stare") && HeroesMod.config().isEnderSoundSwapEnabled()) {
			ISound orig = event.sound;
			ISound sound = new PositionedSoundRecord(
					new ResourceLocation(Sounds.BANSHEE_SCREAM), 
					orig.getVolume() * 2.0F, orig.getPitch(), orig.getXPosF(), orig.getYPosF(), orig.getZPosF());

			event.result = sound;
		}
		
	}
}
