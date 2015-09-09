package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

import com.himself12794.heroesmod.Powers;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersWrapper;
import com.himself12794.powersapi.util.UsefulMethods;

public class BlockMemory extends PowerInstant {

	public BlockMemory() {
		setUnlocalizedName("blockMemory");
		setRange(5);
		setMaxFunctionalState(1);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier, int state) {

		PowersWrapper wrap = PowersWrapper.get(caster);
		boolean result;
		
		if (state == 0) {
			result = rememberBlock(wrap, target, world);
		} else {
			result = recallBlock(wrap, target, world);
		}
		
		if (result) {
			wrap.getPowerProfile(this).cycleState(false);
		}
		
		return result;

	}
	
	@Override
	public void onStateChanged(World world, EntityLivingBase caster, int prevState, int currState) {
		
		if (getMaxFunctionalState() <= 1) {
			String text;
			
			if (currState == 0) {
				text = "Now remebering blocks";
			} else {
				text = "Now recalling blocks";
			}
			if (world.isRemote) caster.addChatMessage( new ChatComponentText( text ) );
		}
		
	}
	
	@Override
	public int getCooldown(PowerProfile profile) {
		
		int uses = profile.getUses();
		
		if (uses < 100) return 40;
		else if (uses < 200) return 30;
		else if (uses < 300) return 20;
		else if (uses < 500) return 10;
		else return 0;
		
	}
	
	private boolean recallBlock(PowersWrapper wrap, MovingObjectPosition target, World world) {

		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			
			PowerProfile powerProf = wrap.getPowerProfile(Powers.BLOCK_MEMORY);
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

						Powers.BLOCK_MEMORY.playSoundAndPoof(world, newPos);

						placementHelp.onItemUse((EntityPlayer) wrap.theEntity, world,
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
						Powers.BLOCK_MEMORY.playSoundAndPoof(world, newPos);
					}
					powerProf.resetPowerData();
					return true;
				}
			} else {
				if (world.isRemote)
					wrap.theEntity.addChatMessage(new ChatComponentText(
							"You don't have any blocks you're remebering"));
			}

		} 

		return false;
		
	}
	
	private boolean rememberBlock(PowersWrapper wrap, MovingObjectPosition target, World world) {
		NBTTagCompound nbt = wrap.getPowerProfile(this).powerData;

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

					return true;
				} else {
					if (world.isRemote)
						wrap.theEntity.addChatMessage(new ChatComponentText(
								"Cannot remember any more blocks. Recall a block before trying to remember another."));
				}
			} else {
				if (world.isRemote)
					wrap.theEntity.addChatMessage(new ChatComponentText(
							EnumChatFormatting.RED
									+ "Cannot remember that block"));
			}

		}
		
		return false;
	}

	private void removeBlockAndPlayEffects(World world, BlockPos pos, Block block) {
		
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

	private void playSoundAndPoof(World world, BlockPos pos) {
		playSound(world, pos);
		playPoof(world, pos);
	}

	private boolean canCloneBlock(Block block, IBlockState state,
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
