package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersWrapper;
import com.himself12794.powersapi.util.UsefulMethods;

// TODO Add tile entity lock to prevent source change
public class BlockRecall extends PowerInstant {

	public BlockRecall() {
		setUnlocalizedName("blockRecall");
		setRange(5);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

			PowersWrapper wrap = PowersWrapper.get(caster);
			PowerProfile powerProf = wrap.getPowerProfile(Powers.BLOCK_REMEMBER);
			NBTTagCompound powerData = powerProf.powerData;

			if (powerData.getBoolean("memoryFull")) {

				BlockPos newPos = UsefulMethods.getBlockFromSideSwap(
						target.getBlockPos(), target.sideHit);

				IBlockState originalState = Block.getStateById(powerData
						.getInteger("savedBlock"));
				Block transportedBlock = originalState.getBlock();

				// Check if block can be placed there
				if (transportedBlock.canPlaceBlockAt(world, newPos)) {

					// It works with tile entities now!
					if (powerData.getBoolean("hasTileEntity")) {

						NBTTagCompound tags = new NBTTagCompound();
						TileEntity entityNew = TileEntity
								.createAndLoadEntity(powerData
										.getCompoundTag("savedTileEntity"));
						entityNew.setPos(newPos);

						Item toItem = Item.getItemFromBlock(transportedBlock);

						ItemStack placementHelp = toItem != null ? new ItemStack(
								toItem) : new ItemStack(transportedBlock);

						Powers.BLOCK_REMEMBER.playSoundAndPoof(world, newPos);

						placementHelp.onItemUse((EntityPlayer) caster, world,
								newPos, target.sideHit,
								(float) target.hitVec.xCoord,
								(float) target.hitVec.yCoord,
								(float) target.hitVec.zCoord);

						if (world.getTileEntity(newPos) != null) {
							world.removeTileEntity(newPos);
							world.setTileEntity(newPos, entityNew);
						}

					} else {

						world.setBlockState(newPos, originalState);
						Powers.BLOCK_REMEMBER.playSoundAndPoof(world, newPos);
					}
					powerProf.resetPowerData();
					return true;
				}
			} else {
				if (world.isRemote)
					caster.addChatMessage(new ChatComponentText(
							"You don't have any blocks you're remebering"));
			}

		} 

		return false;

	}

}
