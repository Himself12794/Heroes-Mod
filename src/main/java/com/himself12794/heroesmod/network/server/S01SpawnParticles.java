package com.himself12794.heroesmod.network.server;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.himself12794.heroesmod.util.EnumRandomType;

public class S01SpawnParticles implements IMessage {

	private EnumParticleTypes particles;
	private double x;
	private double y;
	private double z;
	private float modifier;
	private int amount;
	private EnumRandomType type;

	public S01SpawnParticles() {

	}

	public S01SpawnParticles(EnumParticleTypes particles, double x,
			double y, double z, float modifier, int amount, EnumRandomType type) {

		this.particles = particles;
		this.x = x;
		this.y = y;
		this.z = z;
		this.modifier = modifier;
		this.amount = amount;
		this.type = type;

	}

	@Override
	public void toBytes(ByteBuf buf) {

		ByteBufUtils.writeVarShort(buf, particles.getParticleID());

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setDouble("x", x);
		nbt.setDouble("y", y);
		nbt.setDouble("z", z);
		nbt.setFloat("modifier", modifier);
		nbt.setInteger("amount", amount);
		nbt.setInteger("type", type.getId());

		ByteBufUtils.writeTag(buf, nbt);

	}

	@Override
	public void fromBytes(ByteBuf buf) {

		particles = EnumParticleTypes.getParticleFromId(ByteBufUtils
				.readVarShort(buf));

		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		x = nbt.getDouble("x");
		y = nbt.getDouble("y");
		z = nbt.getDouble("z");
		modifier = nbt.getFloat("modifier");
		amount = nbt.getInteger("amount");
		type = EnumRandomType.fromId(nbt.getInteger("type"));

	}

	public static class Handler implements
			IMessageHandler<S01SpawnParticles, IMessage> {

		@Override
		public IMessage onMessage(final S01SpawnParticles message, MessageContext ctx) {

			if (ctx.side.isClient()) {

				if (message.particles == null) return null;
				
				Runnable task = new Runnable() {

					@Override
					public void run() {				
					
						for (int i = 0; i < message.amount; ++i) {
							double x = message.x + message.modifier * getRandomFromType(Minecraft.getMinecraft().theWorld.rand, message.type);
							double y = message.y + message.modifier * getRandomFromType(Minecraft.getMinecraft().theWorld.rand, message.type);
							double z = message.z + message.modifier * getRandomFromType(Minecraft.getMinecraft().theWorld.rand, message.type);
							Minecraft.getMinecraft().theWorld.spawnParticle(message.particles, x, y, z, 0, 0, 0);
						}
						
					}
					
				};
				
				Minecraft.getMinecraft().addScheduledTask(task);

			}
			return null;
		}
		
		private double getRandomFromType(Random rand, EnumRandomType type) {
			if (type.equals(EnumRandomType.GAUSSIAN)) {
				return rand.nextGaussian();
			} else {
				return rand.nextDouble();
			}
		}
	}

}
