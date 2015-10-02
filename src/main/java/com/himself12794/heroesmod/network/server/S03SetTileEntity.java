package com.himself12794.heroesmod.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.himself12794.heroesmod.HeroesMod;

public class S03SetTileEntity implements IMessage {

	private TileEntity entity;
	
	public S03SetTileEntity() {}
	
	public S03SetTileEntity(TileEntity entity) {
		this.entity = entity;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		
		NBTTagCompound tag = new NBTTagCompound();
		entity.writeToNBT(tag);
		ByteBufUtils.writeTag(buf, tag);

	}

	@Override
	public void fromBytes(ByteBuf buf) {

		entity = TileEntity.createAndLoadEntity(ByteBufUtils.readTag(buf));
		
	}
	
	public static class Handler implements IMessageHandler<S03SetTileEntity, IMessage> {

		@Override
		public IMessage onMessage(final S03SetTileEntity message, final MessageContext ctx) {
			
			if (ctx.side.isClient()) {
				
				HeroesMod.proxy().scheduleTaskBasedOnContext(ctx, new Runnable() {
					
					@Override
					public void run() {
						
						World world = HeroesMod.proxy().getPlayerFromContext(ctx).getEntityWorld();
						TileEntity entity = message.entity;
						world.removeTileEntity(entity.getPos());
						world.setTileEntity(entity.getPos(), entity);
						
					}
					
				});
				
			}
			
			return null;
		}
		
	}

}
