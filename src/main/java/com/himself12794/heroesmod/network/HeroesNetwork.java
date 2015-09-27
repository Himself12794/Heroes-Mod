package com.himself12794.heroesmod.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.himself12794.heroesmod.network.server.S01SpawnParticles;
import com.himself12794.heroesmod.network.server.S02ExplosionParticles;
import com.himself12794.heroesmod.network.server.S03SetTileEntity;


public final class HeroesNetwork {
	
	private static boolean isInit = false;
	private static boolean messagesRegistered = false;
	private static int currId = 0;
	private static Client CLIENT;
	private static Server SERVER;
	private static SimpleNetworkWrapper wrapper;

	public static Client client() {
		if (isInit) {
			return CLIENT;
		} else {
			return null;
		}
		
	}

	public static Server server() {
		if (isInit) {
			return SERVER;
		} else {
			return null;
		}
		
	}
	
	public static SimpleNetworkWrapper get() {
		return wrapper;
	}
	
	public static void init(SimpleNetworkWrapper wrapper) {
		
		if (!isInit) {
			HeroesNetwork.wrapper = wrapper;
			CLIENT = new Client(wrapper);
			SERVER = new Server(wrapper);
			isInit = true;
		}
	}
	
	public static void registerMessages() {
		
		if (isInit && !messagesRegistered) {

			wrapper.registerMessage( S01SpawnParticles.Handler.class,
					S01SpawnParticles.class, ++currId, Side.CLIENT );
			wrapper.registerMessage( S02ExplosionParticles.Handler.class,
					S02ExplosionParticles.class, ++currId, Side.CLIENT );
			wrapper.registerMessage( S03SetTileEntity.Handler.class,
					S03SetTileEntity.class, ++currId, Side.CLIENT );
			
			messagesRegistered = true;
		}
		
	}
	
}
