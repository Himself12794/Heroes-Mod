package com.himself12794.heroesmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.himself12794.powersapi.PowersAPI;

public class SyncNBTData implements IMessage {
	
	private NBTTagCompound nbttags;
	
    public SyncNBTData() {  }
    
    public SyncNBTData(NBTTagCompound nbttags) { 
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
	
	public static class Handler implements IMessageHandler<SyncNBTData, IMessage> {
       
        @Override
        public IMessage onMessage(SyncNBTData message, MessageContext ctx) {
        	if (ctx.side.isClient()) {
        		
        		if (PowersAPI.proxy.getPlayer() != null) {
        			//DWrapper.set( PowersAPI.proxy.getPlayer(), message.nbttags );
        		}
        	}
        	
        	return null;
        }
	}
}
