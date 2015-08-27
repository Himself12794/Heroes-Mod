package com.himself12794.heroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpawnParticlesClient implements IMessage {
	
	private EnumParticleTypes particles;
	private double x;
	private double y;
	private double z;
	
	public SpawnParticlesClient () {
		
	}
	
	public SpawnParticlesClient(EnumParticleTypes particles, double x, double y, double z) {
		
		this.particles = particles;
		this.x = x;
		this.y = y;
		this.z = z;
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		ByteBufUtils.writeVarShort(buf, particles.getParticleID());
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("x", x);
		nbt.setDouble("y", y);
		nbt.setDouble("z", z);
		
		ByteBufUtils.writeTag(buf, nbt);
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		particles = EnumParticleTypes.getParticleFromId(ByteBufUtils.readVarShort(buf));
		
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		x = nbt.getDouble("x");
		y = nbt.getDouble("y");
		z = nbt.getDouble("z");
		
	}
	
	public static class Handler implements IMessageHandler<SpawnParticlesClient, IMessage> {
       
        @Override
        public IMessage onMessage(SpawnParticlesClient message, MessageContext ctx) {
        	
        	if (ctx.side.isClient()) {
        		
        		Minecraft.getMinecraft().theWorld.spawnParticle(message.particles, message.x, message.y, message.z, 0, 0, 0);
    
        	}
        	return null;
        }
	}



}
