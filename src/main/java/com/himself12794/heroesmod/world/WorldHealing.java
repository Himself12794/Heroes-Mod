package com.himself12794.heroesmod.world;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.heroesmod.util.UtilMethods;
import com.himself12794.powersapi.util.UsefulMethods;

public class WorldHealing extends WorldSavedData {

	private static final int INIT_DELAY = 40;
	private static final int HEAL_DELAY = 0;

	private final List<ExplosionEntry> explosions;
	
	public WorldHealing(String name) {
		super(name);
		explosions = Lists.newArrayList();
	}
	
	public void addExplosion(NoDropsExplosion explosion) {
		explosions.add(new ExplosionEntry(explosion));
	}
	
	public void doHeal(WorldServer world) {
		
		Set toRemove = Sets.newHashSet();
		
		for (ExplosionEntry entry : explosions) {
			
			if (!entry.done) {
				entry.doHeal(world);
			} else {
				toRemove.add(entry);
			}
			
		}
		
		for (Object entry : toRemove) {
			
			if (explosions.contains(entry)){
				explosions.remove(entry);
			}
			
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		NBTTagList total = UsefulMethods.getPutKeyList(mapName, nbt, 10);
		
		for (ExplosionEntry entry : explosions) {
			
			NBTTagCompound tag = new NBTTagCompound();
			entry.writeToNBT(tag);
			
			total.appendTag(tag);
			
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		if (nbt.hasKey(mapName, 9)) {
			
			NBTTagList total = nbt.getTagList(Reference.MODID + ":" + mapName, 10);
			for (NBTTagCompound tag : UtilMethods.getIterable(total)) {
				
				ExplosionEntry entry = new ExplosionEntry();
				entry.readFromNBT(tag);
				
				explosions.add(entry);
			}
			
		}
		
	}
	
	private static class ExplosionEntry {
		
		private final Map<BlockPos, IBlockState> blocks;
		private final Map<BlockPos, TileEntity> tileEntities;
		private int healDelay;
		private int delayInit = INIT_DELAY;
		private boolean done;
		
		private ExplosionEntry() {
			blocks = Maps.newHashMap();
			tileEntities = Maps.newHashMap();
		}
		
		private ExplosionEntry(NoDropsExplosion ex) {

			blocks = Maps.newHashMap(ex.getBlockStates());
			tileEntities = Maps.newHashMap(ex.getTileEntities());
			//orderBlocks();
		}
		
		private void orderBlocks() {
			
			Map<BlockPos, IBlockState> airBlocks = Maps.newHashMap();
			Map<BlockPos, IBlockState> fullBlocks = Maps.newHashMap();
			Map<BlockPos, IBlockState> fallingBlocks = Maps.newHashMap();
			Map<BlockPos, IBlockState> nonFullBlocks = Maps.newHashMap();
			
			for (Map.Entry<BlockPos, IBlockState> entry : blocks.entrySet()) {
				
				Block block = entry.getValue().getBlock();
				
				if (block == Blocks.air) airBlocks.put(entry.getKey(), entry.getValue());
				else if (block instanceof BlockFalling) fallingBlocks.put(entry.getKey(), entry.getValue());
	    		else if (block.isFullBlock()) fullBlocks.put(entry.getKey(), entry.getValue());
	    		else nonFullBlocks.put(entry.getKey(), entry.getValue());
				
			}
			
			blocks.clear();
			blocks.putAll(airBlocks);
			blocks.putAll(fullBlocks);
			blocks.putAll(fallingBlocks);
			blocks.putAll(nonFullBlocks);
			
			HeroesMod.logger().info("Blocks have been ordered");
			for (IBlockState state : blocks.values()) {
				HeroesMod.logger().info(state.getBlock());
			}
		}
		
		private Map<BlockPos, IBlockState> orderByPos(Map<BlockPos, IBlockState> map) {
			
			Comparator<BlockPos> posComparator = new Comparator<BlockPos>() {

				@Override
				public int compare(BlockPos o1, BlockPos o2) {
					return o1.compareTo(o2);
				}
				
			};
			
			Map<BlockPos, IBlockState> newPositions = Maps.newTreeMap(posComparator);
			newPositions.putAll(map);	
			
			return newPositions;
			
		}
		
		private void doHeal(WorldServer world) {
			
			if (healDelay <= 0 && !done && delayInit <= 0) {
				BlockPos toRemove = null;
				
				Comparator<BlockPos> posComparator = new Comparator<BlockPos>() {

					@Override
					public int compare(BlockPos o1, BlockPos o2) {
						return o1.compareTo(o2);
					}
					
				};
				

				List<Map<BlockPos, IBlockState>> order = Lists.newArrayList();
				Map<BlockPos, IBlockState> fullBlocks = Maps.newHashMap();
				Map<BlockPos, IBlockState> fallingBlocks = Maps.newTreeMap(posComparator);
				Map<BlockPos, IBlockState> nonFullBlocks = Maps.newHashMap();
				order.add(fullBlocks);
				order.add(fallingBlocks);
				order.add(nonFullBlocks);
				
				for (Map.Entry<BlockPos, IBlockState> entry : blocks.entrySet()) {
					
					Block block = entry.getValue().getBlock();
					
					if (block == Blocks.air) continue;
					else if (block instanceof BlockFalling) fallingBlocks.put(entry.getKey(), entry.getValue());
		    		else if (block.isFullBlock()) fullBlocks.put(entry.getKey(), entry.getValue());
		    		else nonFullBlocks.put(entry.getKey(), entry.getValue());
					
				}
				
				out:
				for (Map<BlockPos, IBlockState> theMap : order) {
				
					for (Map.Entry<BlockPos, IBlockState> entry : theMap.entrySet()) {
						
						toRemove = entry.getKey();
						if (world.getBlockState(toRemove).getBlock().getMaterial().isReplaceable()) { 
							world.setBlockState(toRemove, entry.getValue());
							
							if (tileEntities.containsKey(toRemove)) {
								TileEntity entity = tileEntities.get(toRemove);
								NBTTagCompound data = new NBTTagCompound();
								entity.writeToNBT(data);
								if (world.getTileEntity(toRemove) != null) {
									world.getTileEntity(toRemove).readFromNBT(data);
								} else {
									HeroesNetwork.client().setTileEntity(entity);
									world.setTileEntity(toRemove, entity);
								}
							}
						}
						
						break out;
					}
				}
				
				if (toRemove != null) { 
					blocks.remove(toRemove);
					if (tileEntities.containsKey(toRemove)) 
						tileEntities.remove(toRemove);
				}
				
				healDelay = HEAL_DELAY;
			} else if (healDelay > 0) --healDelay;
			
			if (delayInit > 0) --delayInit;
			if (blocks.entrySet().size() <= 0) done = true;
		}

		private NBTTagList getBlockMapAsNBTList() {
			NBTTagList nbt = new NBTTagList();
			
			for (Map.Entry<BlockPos, IBlockState> entry : blocks.entrySet()) {
				
				NBTTagCompound item = new NBTTagCompound();
				
				BlockPos pos = entry.getKey();
				item.setIntArray("BlockPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				
				int stateId = Block.getStateId(entry.getValue());
				item.setInteger("BlockState", stateId);
				nbt.appendTag(item);
				
			}
			
			return nbt;
		}
		
		private NBTTagList getTileEntityMapAsNBTList() {
			NBTTagList nbt = new NBTTagList();
			
			for (Map.Entry<BlockPos, TileEntity> entry : tileEntities.entrySet()) {
				
				NBTTagCompound item = new NBTTagCompound();
				TileEntity tileEntity = entry.getValue();
				BlockPos pos = entry.getKey();
				NBTTagCompound entityNBT = new NBTTagCompound();
				
				item.setIntArray("BlockPos", new int[]{pos.getX(), pos.getY(), pos.getZ()});
				tileEntity.writeToNBT(entityNBT);
				item.setTag("TileEntity", entityNBT);
				
				nbt.appendTag(item);
			}
			
			return nbt;
		}
		
		private void setBlocksMap(NBTTagList list) {
			
			for (NBTTagCompound compound : UtilMethods.getIterable(list)) {
				
				IBlockState state = Block.getStateById(compound.getInteger("BlockState"));
				
				int[] vals = compound.getIntArray("BlockPos");
				BlockPos pos = new BlockPos(vals[0],vals[1], vals[2]);
				
				blocks.clear();
				blocks.put(pos, state);
				
			}
		}
		
		private void setTileEntitiesMap(NBTTagList list) {
			
			for (NBTTagCompound compound : UtilMethods.getIterable(list)) {
				
				TileEntity entity = TileEntity.createAndLoadEntity(compound.getCompoundTag("TileEntity"));
				
				int[] vals = compound.getIntArray("BlockPos");
				BlockPos pos = new BlockPos(vals[0],vals[1], vals[2]);
				
				tileEntities.clear();
				tileEntities.put(pos, entity);
				
			}
		}
		
		public void writeToNBT(NBTTagCompound nbt) {
			
			nbt.setTag("Blocks", getBlockMapAsNBTList());
			nbt.setTag("TileEntities", getTileEntityMapAsNBTList());
			nbt.setInteger("InitDelay", delayInit);
			
			
		}

		public void readFromNBT(NBTTagCompound nbt) {
				
			setBlocksMap(nbt.getTagList("Blocks", 10));
			setTileEntitiesMap(nbt.getTagList("TileEntities", 10));
			delayInit = nbt.getInteger("InitDelay");
		
		}
		
	}

}
