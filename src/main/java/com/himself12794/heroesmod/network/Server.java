package com.himself12794.heroesmod.network;

import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import com.himself12794.powersapi.network.client.C01PowerUse;
import com.himself12794.powersapi.network.client.C02SetMouseOverTarget;
import com.himself12794.powersapi.network.client.C03CyclePowerState;
import com.himself12794.powersapi.power.Power;

public class Server {

	private final SimpleNetworkWrapper network;

	Server(SimpleNetworkWrapper network) {

		this.network = network;
	}
	
}
