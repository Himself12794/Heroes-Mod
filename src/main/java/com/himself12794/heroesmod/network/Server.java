package com.himself12794.heroesmod.network;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Server {

	@SuppressWarnings("unused")
	private final SimpleNetworkWrapper network;

	Server(SimpleNetworkWrapper network) {

		this.network = network;
	}
	
}
