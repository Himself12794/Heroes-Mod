package com.himself12794.heroesmod.network.server;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.google.common.collect.Lists;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.heroesmod.world.NoDropsExplosion;
import com.himself12794.powersapi.util.UsefulMethods;

public class S02ExplosionParticles implements IMessage {

	private final List<BlockPos> affectedPos;
	private boolean isSmoking;
	private float size;
	private Vec3 position;
	
	public S02ExplosionParticles() {
		affectedPos = Lists.newArrayList();
	}
	
	@SuppressWarnings("unchecked")
	public S02ExplosionParticles(NoDropsExplosion explosion) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		affectedPos = explosion.func_180343_e();
		isSmoking = explosion.isSmoking;
		size = explosion.getSize();
		position = explosion.getPosition();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		NBTTagList list = new NBTTagList();
		
		for (BlockPos pos : affectedPos) {
			NBTTagCompound pos2 = new NBTTagCompound();
			pos2.setIntArray("Pos", new int[] { pos.getX(), pos.getY(), pos.getZ() });
			
			list.appendTag(pos2);
		}
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("Positions", list);
		ByteBufUtils.writeTag(buf, tag);
		buf.writeBoolean(isSmoking);
		buf.writeFloat(size);
		buf.writeDouble(position.xCoord);
		buf.writeDouble(position.yCoord);
		buf.writeDouble(position.zCoord);
		
	}

	@Override
	public void fromBytes(ByteBuf buf) {

		NBTTagCompound tag = ByteBufUtils.readTag(buf);
		NBTTagList list = tag.getTagList("Positions", 10);
		
		for (NBTTagCompound position : UtilMethods.getIterable(list))
			affectedPos.add(UsefulMethods.getPosFromArray(position.getIntArray("Pos")));
		
		isSmoking = buf.readBoolean();
		size = buf.readFloat();
		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();
		position = new Vec3(x, y, z);
			
	}
	
	public static class Handler implements IMessageHandler<S02ExplosionParticles,IMessage> {

		@Override
		public IMessage onMessage(final S02ExplosionParticles message, final MessageContext ctx) {
			
			if (ctx.side.isClient()) {
				
				HeroesMod.proxy().scheduleTaskBasedOnContext(ctx, new Runnable() {

					@Override
					public void run() {
						World world = HeroesMod.proxy().getPlayerFromContext(ctx).getEntityWorld();
						Vec3 pos = message.position;
						Explosion explosion = new Explosion(world, null, pos.xCoord, pos.yCoord, pos.zCoord, message.size, false, message.isSmoking);
						try {
							(new NoDropsExplosion(explosion)).setHarmless().doParticles(message.affectedPos, true);
						} catch (Exception e) {
							HeroesMod.logger().error("Could not do explosion particles", e);
						} 
					}
					
				});
				
			}
			
			return null;
		}
		
	}

}
