package com.himself12794.heroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncNBTDataToServer implements IMessage {
	
	private NBTTagCompound nbttags;
	
    public SyncNBTDataToServer() {  }
    
    public SyncNBTDataToServer(NBTTagCompound nbttags) { 
    	this.nbttags = nbttags;
    }

	@Override
	public void toBytes(ByteBuf buf) {	
		ByteBufUtils.writeTag( buf, nbttags );
	}

	@Override
	public void fromBytes(ByteBuf buf) { 
		nbttags = ByteBufUtils.readTag( buf );
	}
	
	public static class Handler implements IMessageHandler<SyncNBTDataToServer, IMessage> {
       
        @Override
        public IMessage onMessage(SyncNBTDataToServer message, MessageContext ctx) {
        	if (ctx.side.isServer()) {
        		
        		if (ctx.getServerHandler().playerEntity != null) {
        			//DWrapper.set( ctx.getServerHandler().playerEntity, message.nbttags );
        		}
        	}
        	
        	return null;
        }
	}
}
