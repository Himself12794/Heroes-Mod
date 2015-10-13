package com.himself12794.heroesmod.world;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import static com.himself12794.heroesmod.util.ReflectUtils.getField;

/**
 * Blocks destroyed by this explosion don't drop anything.
 * 
 * @author Himself12794
 *
 */
public class NoDropsExplosion extends Explosion {

    /** whether or not the explosion sets fire to blocks around it */
    private final boolean isFlaming;
    /** whether or not this explosion spawns smoke particles */
    public final boolean isSmoking;
    private final Random explosionRNG;
    private final World worldObj;
    private final double explosionX;
    private final double explosionY;
    private final double explosionZ;
    private final Entity exploder;
    private final float explosionSize;
    /** A list of ChunkPositions of blocks affected by this explosion */
    private final List affectedBlockPositions;
    private final Map field_77288_k;
    private final Vec3 position;
    private boolean harmless;
    private final Map<BlockPos, IBlockState> blocks;
    private final Map<BlockPos, TileEntity> tileEntities;

	public NoDropsExplosion(Explosion e) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		super((World)getField(e, "worldObj", "field_77287_j"), (Entity)getField(e, "exploder", "field_77283_e"), (Double)getField(e, "explosionX", "field_77284_b"), (Double)getField(e, "explosionY", "field_77285_c"), (Double)getField(e, "explosionZ", "field_77282_d"), (Float)getField(e, "explosionSize", "field_77280_f"), (Boolean)getField(e, "isFlaming", "field_77286_a"), (Boolean)getField(e, "isSmoking", "field_82755_b"));
		
		this.explosionRNG = (Random) getField(this, "explosionRNG", "field_77290_i");
        this.affectedBlockPositions = (List) getField(this, "affectedBlockPositions", "field_77281_g");
        this.field_77288_k = (Map) getField(this, "field_77288_k", null);
		this.worldObj = (World) getField(this, "worldObj", "field_77287_j");
		this.exploder = (Entity) getField(this, "exploder", "field_77283_e");
		this.explosionSize = (Float) getField(this, "explosionSize", "field_77280_f");
		this.explosionX = (Double) getField(this, "explosionX", "field_77284_b");
		this.explosionY = (Double) getField(this, "explosionY", "field_77285_c");
		this.explosionZ = (Double) getField(this, "explosionZ", "field_77282_d");
		this.isSmoking = (Boolean) getField(this, "isSmoking", "field_82755_b");
		this.isFlaming = (Boolean) getField(this, "isFlaming", "field_77286_a");
		this.position = new Vec3(explosionX, explosionY, explosionZ);
		this.blocks = Maps.newHashMap();
		this.tileEntities = Maps.newHashMap();
	}

	public NoDropsExplosion setHarmless() {
		harmless = true;
		return this;
	}
	
	public float getSize() {
		return this.explosionSize;
	}
	
    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        HashSet hashset = Sets.newHashSet();
        boolean flag = true;
        int j;
        int k;

        for (int i = 0; i < 16; ++i)
        {
            for (j = 0; j < 16; ++j)
            {
                for (k = 0; k < 16; ++k)
                {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15)
                    {
                        double d0 = (double)((float)i / 15.0F * 2.0F - 1.0F);
                        double d1 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                        double d2 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        double d4 = this.explosionX;
                        double d6 = this.explosionY;
                        double d8 = this.explosionZ;

                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F)
                        {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            IBlockState iblockstate = this.worldObj.getBlockState(blockpos);

                            if (iblockstate.getBlock().getMaterial() != Material.air)
                            {
                                float f2 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(worldObj, blockpos, (Entity)null, this);
                                f -= (f2 + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && (this.exploder == null || this.exploder.func_174816_a(this, this.worldObj, blockpos, iblockstate, f)))
                            {
                                hashset.add(blockpos);
                            }

                            d4 += d0 * 0.30000001192092896D;
                            d6 += d1 * 0.30000001192092896D;
                            d8 += d2 * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        float f3 = this.explosionSize * 2.0F;
        j = MathHelper.floor_double(this.explosionX - (double)f3 - 1.0D);
        k = MathHelper.floor_double(this.explosionX + (double)f3 + 1.0D);
        int j1 = MathHelper.floor_double(this.explosionY - (double)f3 - 1.0D);
        int l = MathHelper.floor_double(this.explosionY + (double)f3 + 1.0D);
        int k1 = MathHelper.floor_double(this.explosionZ - (double)f3 - 1.0D);
        int i1 = MathHelper.floor_double(this.explosionZ + (double)f3 + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double)j, (double)j1, (double)k1, (double)k, (double)l, (double)i1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, f3);
        Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);

        if (harmless) return;
        
        for (int l1 = 0; l1 < list.size(); ++l1)
        {
            Entity entity = (Entity)list.get(l1);

            if (!entity.func_180427_aV())
            {
                double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)f3;

                if (d12 <= 1.0D)
                {
                    double d5 = entity.posX - this.explosionX;
                    double d7 = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
                    double d9 = entity.posZ - this.explosionZ;
                    double d13 = (double)MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);

                    if (d13 != 0.0D)
                    {
                        d5 /= d13;
                        d7 /= d13;
                        d9 /= d13;
                        double d14 = (double)this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
                        double d10 = (1.0D - d12) * d14;
                        entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((d10 * d10 + d10) / 2.0D * 8.0D * (double)f3 + 1.0D)));
                        double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                        entity.motionX += d5 * d11;
                        entity.motionY += d7 * d11;
                        entity.motionZ += d9 * d11;

                        if (entity instanceof EntityPlayer)
                        {
                            this.field_77288_k.put((EntityPlayer)entity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }
        }
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean doParticles)
    {
    	
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
        }
        else
        {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
        }

        ListIterator iterator;
        BlockPos blockpos;

        if (this.isSmoking)
        {
        	List newBlocks = this.orderedBlocks();
            iterator = newBlocks.listIterator();
            
            for (;iterator.hasNext();iterator.next());
            
            while (iterator.hasPrevious())
            {
                blockpos = (BlockPos)iterator.previous();
                IBlockState state = this.worldObj.getBlockState(blockpos);
                Block block = state.getBlock();

                if (doParticles)
                {
                    double d0 = (double)((float)blockpos.getX() + this.worldObj.rand.nextFloat());
                    double d1 = (double)((float)blockpos.getY() + this.worldObj.rand.nextFloat());
                    double d2 = (double)((float)blockpos.getZ() + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                    d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
                    this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                }

                if (block.getMaterial() != Material.air && !harmless)
                {
                    if (block.canDropFromExplosion(this))
                    {
                    	TileEntity entity = worldObj.getTileEntity(blockpos);
                    	
                    	if (entity != null) {
                    		tileEntities.put(blockpos, entity);
                    		worldObj.removeTileEntity(blockpos);
                    	}
                        //block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
                    	worldObj.setBlockToAir(blockpos);
                    	blocks.put(blockpos, state);
                    }

                    block.onBlockExploded(this.worldObj, blockpos, this);
                }
            }
            
            affectedBlockPositions.clear();
            affectedBlockPositions.addAll(newBlocks);
        }

        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.listIterator();

            while (iterator.hasNext())
            {
                blockpos = (BlockPos)iterator.next();

                if (this.worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air && this.worldObj.getBlockState(blockpos.down()).getBlock().isFullBlock() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
                }
            }
        }
    }
    
    public List<BlockPos> orderedBlocks() {

    	final List<BlockPos> airBlocks = Lists.newArrayList();
    	final List<BlockPos> fallingBlocks = Lists.newArrayList();
    	final List<BlockPos> fullBlocks = Lists.newArrayList();
    	final List<BlockPos> nonFullBlocks = Lists.newArrayList();
    	
    	Iterator iterator = affectedBlockPositions.iterator();
    	while (iterator.hasNext()) {
    		BlockPos pos = (BlockPos) iterator.next();
    		Block block = worldObj.getBlockState(pos).getBlock();
    		
    		if (block == Blocks.air) airBlocks.add(pos);
    		else if (block instanceof BlockFalling) fallingBlocks.add(pos);
    		else if (block.isFullBlock()) fullBlocks.add(pos);
    		else nonFullBlocks.add(pos);
    	}
    	airBlocks.addAll(fullBlocks);
    	airBlocks.addAll(fallingBlocks);
    	airBlocks.addAll(nonFullBlocks);
    	
    	return airBlocks;
    }
    
    public void doParticles(List affectedBlockPositions, boolean doParticles) {
    	 if (this.explosionSize >= 2.0F && this.isSmoking)
         {
             this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
         }
         else
         {
             this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
         }

         Iterator iterator;
         BlockPos blockpos;

         if (this.isSmoking)
         {
             iterator = affectedBlockPositions.iterator();
             
             while (iterator.hasNext())
             {
                 blockpos = (BlockPos)iterator.next();
                 IBlockState state = this.worldObj.getBlockState(blockpos);
                 Block block = state.getBlock();

                 if (doParticles)
                 {
                     double d0 = (double)((float)blockpos.getX() + this.worldObj.rand.nextFloat());
                     double d1 = (double)((float)blockpos.getY() + this.worldObj.rand.nextFloat());
                     double d2 = (double)((float)blockpos.getZ() + this.worldObj.rand.nextFloat());
                     double d3 = d0 - this.explosionX;
                     double d4 = d1 - this.explosionY;
                     double d5 = d2 - this.explosionZ;
                     double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                     d3 /= d6;
                     d4 /= d6;
                     d5 /= d6;
                     double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                     d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                     d3 *= d7;
                     d4 *= d7;
                     d5 *= d7;
                     this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
                     this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                 }

                 if (block.getMaterial() != Material.air && !harmless)
                 {
                     if (block.canDropFromExplosion(this))
                     {
                     	TileEntity entity = worldObj.getTileEntity(blockpos);
                         //block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
                     	worldObj.setBlockToAir(blockpos);
                     	blocks.put(blockpos, state);
                     	
                     	if (entity != null) {
                     		tileEntities.put(blockpos, entity);
                     	}
                     }

                     block.onBlockExploded(this.worldObj, blockpos, this);
                 }
             }
         }
    }
    
    public Map<BlockPos, IBlockState> getBlockStates() {
    	return this.blocks;
    }
    
    public Map<BlockPos, TileEntity> getTileEntities() {
    	return this.tileEntities;
    }



}
