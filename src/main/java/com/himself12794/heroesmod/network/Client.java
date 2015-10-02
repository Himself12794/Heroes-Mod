package com.himself12794.heroesmod.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import com.himself12794.heroesmod.network.server.S01SpawnParticles;
import com.himself12794.heroesmod.network.server.S02ExplosionParticles;
import com.himself12794.heroesmod.network.server.S03SetTileEntity;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.world.NoDropsExplosion;


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
	
	public void spawnParticles(EnumParticleTypes particles, double x, double y, double z, float modifierX, float modifierY, float modifierZ, int amount, EnumRandomType type, TargetPoint point) {
		IMessage message = new S01SpawnParticles(particles, x, y, z, modifierX, modifierY, modifierZ, amount, type);
		if (point != null) network.sendToAllAround(message, point);
		else network.sendToAll(message);
	}
	
	public void explosionParticles(NoDropsExplosion explosion) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		network.sendToAll(new S02ExplosionParticles(explosion));
	}
	
	public void setTileEntity(TileEntity entity) {
		network.sendToAll(new S03SetTileEntity(entity));
	}

}
