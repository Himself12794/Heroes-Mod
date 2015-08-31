package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.util.DWrapper;
import com.himself12794.powersapi.item.ModItems;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.UsefulMethods;

// TODO Add tile entity lock to prevent source change
public class BlockRecall extends PowerInstant {

	public BlockRecall() {
		setUnlocalizedName("blockRecall");
		setRange(5);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		DWrapper wrap = DWrapper.get(caster);

		// Check if no other block pos is saved
		if (!wrap.getSavedBlockPos1().equals(BlockPos.ORIGIN)) {

			// Check if selection is a block pos
			if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

				BlockPos newPos = UsefulMethods.getBlockFromSideSwap(
						target.getBlockPos(), target.sideHit);

				BlockPos originalPos = wrap.getSavedBlockPos1();
				IBlockState originalState = world.getBlockState(originalPos);
				Block transportedBlock = originalState.getBlock();
				originalState = transportedBlock.getActualState(originalState,
						world, originalPos);
				SoundType sound = transportedBlock.stepSound;

				// Check if block can be placed there
				if (transportedBlock.canPlaceBlockAt(world, newPos)) {

					// Check if source block is still valid
					if (canCloneBlock(transportedBlock, originalState,
							originalPos, world)) {

						// It works with tile entities now!
						if (transportedBlock instanceof ITileEntityProvider
								&& transportedBlock
										.hasTileEntity(originalState)) {

							NBTTagCompound tags = new NBTTagCompound();
							TileEntity entityOld = world
									.getTileEntity(originalPos);
							TileEntity entityNew = null;

							entityOld.setPos(newPos);
							entityOld.writeToNBT(tags);

							removeBlockAndPlayEffects(world, originalPos, sound);
							updateSurroundingBlocks(world, originalPos,
									transportedBlock);

							Item toItem = Item
									.getItemFromBlock(transportedBlock);

							ItemStack placementHelp = toItem != null ? new ItemStack(
									toItem) : new ItemStack(transportedBlock);
							playSoundAndPoof(world, newPos, sound);
							placementHelp.onItemUse((EntityPlayer) caster,
									world, newPos, target.sideHit,
									(float) target.hitVec.xCoord,
									(float) target.hitVec.yCoord,
									(float) target.hitVec.zCoord);

							entityNew = world.getTileEntity(newPos);
							if (entityNew != null)
								entityNew.readFromNBT(tags);

						} else {

							removeBlockAndPlayEffects(world, originalPos, sound);
							world.setBlockState(newPos, originalState);
							playSoundAndPoof(world, newPos, sound);
						}

						wrap.setSavedBlockPos1(newPos);

						// Original block has changed and is no longer movable
					} else {
						if (world.isRemote)
							caster.addChatMessage(new ChatComponentText(
									EnumChatFormatting.RED
											+ "The remebered block has changed and cannot be recalled"));

						wrap.setSavedBlockPos1(null);
					}

					return true;
					// Original block can't be placed at the new location
				} /*
				 * else { if (world.isRemote) caster.addChatMessage(new
				 * ChatComponentText( EnumChatFormatting.RED +
				 * "The block can't be placed there")); }
				 */
			}

		} else {
			if (world.isRemote)
				caster.addChatMessage(new ChatComponentText(
						"You don't have any blocks you're remebering"));
		}

		return false;

	}

	private void playSound(World world, BlockPos pos, SoundType sound) {

		world.playSound((double) pos.getX(), (double) pos.getY(),
				(double) pos.getZ(), "mob.endermen.portal", 0.5F, 1.0F, false);

		// world.playSound((double) pos.getX(), (double) pos.getY(),
		// (double) pos.getZ(), sound.getPlaceSound(), sound.volume,
		// sound.frequency, false);

	}

	private void playPoof(World world, BlockPos pos) {

		float x = pos.getX();
		float y = pos.getY();
		float z = pos.getZ();

		for (int i = 0; i < 25; i++) {

			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, x + 0.5D
					* world.rand.nextGaussian(),
					y + 1.5D * world.rand.nextGaussian(),
					z + 0.5D * world.rand.nextGaussian(), 0, 0, 0);

		}

	}

	private void playSoundAndPoof(World world, BlockPos pos, SoundType sound) {
		playSound(world, pos, sound);
		playPoof(world, pos);
	}

	public static boolean canCloneBlock(Block block, IBlockState state,
			BlockPos pos, World world) {

		float hardness = block.getBlockHardness(world, pos);
		boolean canClone = true;
		
		// General restrictions
		canClone &= !block.isReplaceable(world, pos);
		canClone &= block.isFullBlock();
		canClone &= hardness >= 0.0F && hardness <= 50.0F;
		canClone &= block.getMaterial().isSolid()
				&& block.getMaterial() != Material.air;
		
		// Specific inclusions / exclusions (blocks with unique items exclude until fixed)
		canClone |= block instanceof ITileEntityProvider
				&& !(block instanceof BlockCommandBlock)
				&& !(block instanceof BlockBanner)
				&& !(block instanceof BlockEndPortal)
				&& !(block instanceof BlockFlowerPot)
				|| block instanceof BlockDragonEgg;
		
		return canClone;

	}

	public void updateSurroundingBlocks(World world, BlockPos originalPos,
			Block block) {

		for (EnumFacing side : EnumFacing.VALUES) {

			BlockPos temp = UsefulMethods.getBlockFromSide(originalPos, side);
			IBlockState state = world.getBlockState(temp);
			state.getBlock().onNeighborBlockChange(world, temp, state, block);

		}

	}

	private void removeBlockAndPlayEffects(World world, BlockPos pos,
			SoundType sound) {

		playSound(world, pos, sound);
		playPoof(world, pos);
		world.playRecord(pos, (String) null);
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
	}

}
