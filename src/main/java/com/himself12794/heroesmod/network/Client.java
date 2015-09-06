package com.himself12794.heroesmod.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import com.himself12794.heroesmod.network.server.S01SpawnParticles;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.powersapi.network.server.S01SyncProperty;
import com.himself12794.powersapi.network.server.S02SetPower;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.storage.PropertiesBase;


public class Client {
	
	private final SimpleNetworkWrapper network;
	
	Client(SimpleNetworkWrapper network) {
		this.network = network;
	}
	
	public void spawnParticles(EnumParticleTypes particles, double x, double y, double z, float modifier, int amount, EnumRandomType type, TargetPoint point) {
		IMessage message = new S01SpawnParticles(particles, x, y, z, modifier, amount, type);
		if (point != null) network.sendToAllAround(message, point);
		else network.sendToAll(message);
	}

}
