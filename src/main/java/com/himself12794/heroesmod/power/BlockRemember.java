package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockFlowerPot;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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

		NBTTagCompound nbt = wrap.getPowerProfile(this).getPowerData();

		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

			BlockPos pos = target.getBlockPos();
			IBlockState theBlockState = world.getBlockState(pos);
			Block theBlock = theBlockState.getBlock();
			theBlockState = theBlock.getActualState(theBlockState,
					world, pos);

			if (canCloneBlock(world.getBlockState(pos).getBlock(),
					world.getBlockState(pos), pos, world)) {

				if (!nbt.getBoolean("memoryFull")) {

					nbt.setBoolean("memoryFull", true);
					nbt.setInteger("savedBlock",
							Block.getStateId(world.getBlockState(pos)));

					if (theBlock.hasTileEntity(theBlockState)) {

						NBTTagCompound tileEntity = new NBTTagCompound();
						world.getTileEntity(pos).writeToNBT(tileEntity);

						nbt.setTag("savedTileEntity", tileEntity);
						nbt.setBoolean("hasTileEntity", true);
						
					}
					
					removeBlockAndPlayEffects(world, pos, theBlock);

					if (world.isRemote)
						caster.addChatMessage(new ChatComponentText(
								"You'll remember that block now"));

					return true;
				} else {
					if (world.isRemote)
						caster.addChatMessage(new ChatComponentText(
								"Cannot remember any more blocks. Recall a block before trying to remember another."));
				}
			} else {
				if (world.isRemote)
					caster.addChatMessage(new ChatComponentText(
							EnumChatFormatting.RED
									+ "Cannot remember that block"));
			}

		}

		return false;

	}

	private void removeBlockAndPlayEffects(World world, BlockPos pos, Block block) {

		System.out.println(world.isRemote);
		
		playSound(world, pos);
		playPoof(world, pos);
		world.playRecord(pos, (String) null);
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
		updateSurroundingBlocks(world, pos, block);
		
	}

	private void playSound(World world, BlockPos pos) {

		world.playSound((double) pos.getX(), (double) pos.getY(),
				(double) pos.getZ(), "mob.endermen.portal", 0.5F, 1.0F, false);
		

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

	private void updateSurroundingBlocks(World world, BlockPos originalPos,
			Block block) {

		for (EnumFacing side : EnumFacing.VALUES) {

			BlockPos temp = UsefulMethods.getBlockFromSide(originalPos, side);
			IBlockState state = world.getBlockState(temp);
			state.getBlock().onNeighborBlockChange(world, temp, state, block);

		}

	}

	void playSoundAndPoof(World world, BlockPos pos) {
		playSound(world, pos);
		playPoof(world, pos);
	}

	public boolean canCloneBlock(Block block, IBlockState state,
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

}
