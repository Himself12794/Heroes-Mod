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
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

public class BlockMemory extends PowerInstant {

	private static final class State {
		private static final int SINGLE_REMEMBER = 0;
		private static final int SINGLE_RECALL = 1;
	}
	
	public BlockMemory() {
		setUnlocalizedName("blockMemory");
		setMaxLevel(3);
		setRange(5);
		setMaxFunctionalState(1);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier, int state) {

		PowersEntity wrap = PowersEntity.get(caster);
		boolean result;
		
		if (state == State.SINGLE_REMEMBER) {
			result = rememberBlock(wrap, target, world);
		} else {
			result = recallBlock(wrap, target, world);
		}
		
		return result;

	}
	
	@Override
	public void onStateChanged(World world, EntityLivingBase caster, int prevState, int currState) {
		
		if (getMaxFunctionalState() <= 1) {
			String text;
			
			if (currState == State.SINGLE_REMEMBER) {
				text = "Now remembering blocks";
			} else {
				text = "Now recalling blocks";
			}
			
			if (world.isRemote) caster.addChatMessage( new ChatComponentText( text ) );
		}
		
	}
	
	@Override
	public int getCost(PowerProfile profile) {
		
		int uses = profile.getUses();
		
		if (uses < 100) return 40;
		else if (uses < 200) return 30;
		else if (uses < 300) return 20;
		else if (uses < 500) return 10;
		else return 0;
		
	}
	
	@Override
	public String getInfo(PowerProfile profile) {
		NBTTagCompound info = getNextBlockState(profile);
		String text;
		
		IBlockState state = Block.getStateById(info.getInteger("blockState"));
		Block block = state.getBlock();
			
		if (block != Blocks.air)
			text = "Next block: " + (new ItemStack(block, 1, block.getMetaFromState(state))).getDisplayName();
		else
			text = "No blocks in memory";
		
		return text + " (" + getBlocksInMemory(profile).tagCount() + "/" + getMaxMemorySpace(profile) + ")";
	}
	
	public String getDisplayName(PowerProfile profile) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedName() + ".name" + profile.getState())).trim();
	}
	
	private int getMaxMemorySpace(PowerProfile profile) {
		return (int) Math.pow(2, profile.level);
	}
	
	public boolean hasBlocksInMemory(PowerProfile profile) {
		return !getBlocksInMemory(profile).hasNoTags();
	}
	
	public boolean isMemoryFull(PowerProfile profile) {
		return getBlocksInMemory(profile).tagCount() >= getMaxMemorySpace(profile);
	}
	
	public NBTTagList getBlocksInMemory(PowerProfile profile) {
		
		NBTTagCompound tags = profile.powerData;
		
		NBTTagList blocks;
		
		if (tags.hasKey("memorizedBlocks", 9)) {
			blocks = tags.getTagList("memorizedBlocks", 10);
		} else {
			blocks = new NBTTagList();
			tags.setTag("memorizedBlocks", blocks);
		}
		
		return blocks;
	}
	
	private boolean addBlockToMemory(PowerProfile profile, IBlockState state, TileEntity entity) {
		
		NBTTagList blocks = getBlocksInMemory(profile);
		
		if (!isMemoryFull(profile)) {
			
			NBTTagCompound blockData = new NBTTagCompound();
			blockData.setBoolean("isValid", true);
			blockData.setInteger("blockState", Block.getStateId(state));
			
			if (entity != null) {
				NBTTagCompound compound = new NBTTagCompound();
				entity.writeToNBT(compound);
				blockData.setTag("tileEntity", compound);
				blockData.setBoolean("hasTileEntity", true);
			}
			
			blocks.appendTag(blockData);
			return true;
		}
		
		return false;	
	}
	
	private NBTTagCompound getNextBlockState(PowerProfile profile) {
		NBTTagList blocks = getBlocksInMemory(profile);
		
		if (!blocks.hasNoTags()) {
			return blocks.getCompoundTagAt(0);
		}
		
		return new NBTTagCompound();
	}
	
	private NBTTagCompound popNextBlockState(PowerProfile profile) {
		NBTTagCompound state = getNextBlockState(profile);
		NBTTagList blocks = getBlocksInMemory(profile);
		
		if (state.getBoolean("isValid")) {
			blocks.removeTag(0);
		}
		
		return state;
	}
	
	@Override
	public void onKnowledgeTick(PowerProfile profile) {
		
		if (hasBlocksInMemory(profile)) {
			
			NBTTagList blocks = getBlocksInMemory(profile);
			
			for (int i = 0; i < blocks.tagCount(); i++) {
				
				NBTTagCompound block = blocks.getCompoundTagAt(i);
				
				if (block.getBoolean("hasTileEntity")) {
					
					TileEntity entity = TileEntity.createAndLoadEntity(block.getCompoundTag("tileEntity"));
					
					if (entity instanceof TileEntityFurnace) {
						entity.setWorldObj(profile.theEntity.getEntityWorld());
						((TileEntityFurnace)entity).update();
					}
					
					NBTTagCompound compound = new NBTTagCompound();
					entity.writeToNBT(compound);
					block.setTag("tileEntity", compound);
					
				}
				
			}
			
		}
		
	}
	
	private boolean recallBlock(PowersEntity wrap, MovingObjectPosition target, World world) {

		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			
			PowerProfile powerProf = wrap.getPowerProfile(this);
			NBTTagCompound nextBlock = this.getNextBlockState(powerProf);
			
			if ( nextBlock.getBoolean("isValid") ) {

				BlockPos newPos = target.getBlockPos().offset(target.sideHit);

				IBlockState originalState = Block.getStateById(nextBlock.getInteger("blockState"));
				Block transportedBlock = originalState.getBlock();

				// Check if block can be placed there
				if (transportedBlock.canPlaceBlockAt(world, newPos)) {
					
					this.popNextBlockState(powerProf);

					// It works with tile entities now!
					if (nextBlock.getBoolean("hasTileEntity")) {

						NBTTagCompound tags = nextBlock.getCompoundTag("tileEntity");
						TileEntity entityNew = TileEntity.createAndLoadEntity(tags);
						if (entityNew != null) entityNew.setPos(newPos);

						Item toItem = Item.getItemFromBlock(transportedBlock);

						ItemStack placementHelp = toItem != null ? new ItemStack(
								toItem) : new ItemStack(transportedBlock);

						playSoundAndPoof(world, newPos);

						placementHelp.onItemUse((EntityPlayer) wrap.theEntity, world,
								newPos, target.sideHit,
								(float) target.hitVec.xCoord,
								(float) target.hitVec.yCoord,
								(float) target.hitVec.zCoord);

						if (world.getTileEntity(newPos) != null && entityNew != null) {
							world.removeTileEntity(newPos);
							world.setTileEntity(newPos, entityNew);
						}

					} else {

						world.setBlockState(newPos, originalState);
						playSoundAndPoof(world, newPos);
					}
					return true;
				}
			} else {
				if (world.isRemote)
					wrap.theEntity.addChatMessage(new ChatComponentText(
							"You don't have any blocks you're remembering"));
			}

		} 

		return false;
		
	}
	
	private boolean rememberBlock(PowersEntity wrap, MovingObjectPosition target, World world) {

		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

			BlockPos pos = target.getBlockPos();
			IBlockState theBlockState = world.getBlockState(pos);
			Block theBlock = theBlockState.getBlock();
			theBlockState = theBlock.getActualState(theBlockState, world, pos);
			PowerProfile profile = wrap.getPowerProfile(this);
			TileEntity entity = null;

			if (this.canCloneBlock(theBlock, theBlockState, pos, world)) {

				if (!this.isMemoryFull(profile)) {

					if (theBlock.hasTileEntity(theBlockState)) {
						entity = world.getTileEntity(pos);
					}
					
					this.addBlockToMemory(profile, theBlockState, entity);
					
					this.removeBlockAndPlayEffects(world, pos, theBlock);

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
		
		playSound(world, pos, false);
		playPoof(world, pos);
		world.playRecord(pos, (String) null);
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
		updateSurroundingBlocks(world, pos, block);
		
	}

	public void playSound(World world, BlockPos pos, boolean clientOnly) {

		if (clientOnly && world.isRemote) {
			world.playSound((double) pos.getX(), (double) pos.getY(),
					(double) pos.getZ(), "mob.endermen.portal", 0.5F, 1.0F, false);
		} else if (!clientOnly){
			world.playSound((double) pos.getX(), (double) pos.getY(),
					(double) pos.getZ(), "mob.endermen.portal", 0.5F, 1.0F, false);
		}
		
	}

	private void playPoof(World world, BlockPos pos) {

		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();

		HeroesNetwork.client().spawnParticles(EnumParticleTypes.EXPLOSION_NORMAL, x, y, z, 0.5F, 1.5F, 0.5F, 25, EnumRandomType.GAUSSIAN, null);
	}

	private void updateSurroundingBlocks(World world, BlockPos originalPos,
			Block block) {

		for (EnumFacing side : EnumFacing.VALUES) {

			BlockPos temp = originalPos.offset( side, -1 );
			IBlockState state = world.getBlockState(temp);
			state.getBlock().onNeighborBlockChange(world, temp, state, block);

		}

	}

	private void playSoundAndPoof(World world, BlockPos pos) {
		playSound(world, pos, false);
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
