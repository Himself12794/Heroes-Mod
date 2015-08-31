package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.util.DWrapper;
import com.himself12794.powersapi.item.ModItems;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.UsefulMethods;

public class BlockRemember extends PowerInstant {

	public BlockRemember() {
		setUnlocalizedName("blockRemember");
		setRange(5);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		DWrapper wrap = DWrapper.get(caster); 
		
		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

			BlockPos pos = target.getBlockPos();
			if (BlockRecall.canCloneBlock(world.getBlockState(pos).getBlock(),
					world.getBlockState(pos), pos, world)) {

				DWrapper.get(caster).setSavedBlockPos1(pos);
				if (world.isRemote) caster.addChatMessage( new ChatComponentText( "You'll remember that block now" ) );
				return true;
			} else {
				if (world.isRemote)
					caster.addChatMessage(new ChatComponentText(
							EnumChatFormatting.RED
									+ "Cannot remember that block"));
			}

		}

		return false;

	}

}
