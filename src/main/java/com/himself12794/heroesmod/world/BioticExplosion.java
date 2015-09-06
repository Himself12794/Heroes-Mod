package com.himself12794.heroesmod.world;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.himself12794.heroesmod.HeroesMod;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.network.server.S01SpawnParticles;
import com.himself12794.heroesmod.util.BioticExplosionDamage;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.Reference.Sounds;

/**
 * Damages like a regular explosion, but its particles and sound are different,
 * and it doesn't damage the caster.
 * 
 * @author Himself12794
 *
 */
public class BioticExplosion extends Explosion {

	/** whether or not the explosion sets fire to blocks around it */
	private final boolean isFlaming;
	/** whether or not this explosion spawns smoke particles */
	private final boolean isSmoking;
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
	private static final String __OBFID = "CL_00000134";
	private final Vec3 position;
	private float limit = 50.0F;

	public BioticExplosion(World worldIn, Entity exploder, double expX,
			double expY, double expZ, float size, boolean isFlaming,
			boolean isSmoking) {

		super(worldIn, exploder, expX, expY, expZ, size, isFlaming, isSmoking);

		this.explosionRNG = new Random();
		this.affectedBlockPositions = Lists.newArrayList();
		this.field_77288_k = Maps.newHashMap();
		this.worldObj = worldIn;
		this.exploder = exploder;
		this.explosionSize = size;
		this.explosionX = expX;
		this.explosionY = expY;
		this.explosionZ = expZ;
		this.isFlaming = isFlaming;
		this.isSmoking = isSmoking;
		this.position = new Vec3(explosionX, explosionY, explosionZ);
	}

	/**
	 * Does the first part of the explosion (destroy blocks)
	 */
	public void doExplosionA() {

		HashSet hashset = Sets.newHashSet();
		boolean flag = true;
		int j;
		int k;

		for (int i = 0; i < 16; ++i) {
			for (j = 0; j < 16; ++j) {
				for (k = 0; k < 16; ++k) {
					if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0
							|| k == 15) {
						double d0 = (double) ((float) i / 15.0F * 2.0F - 1.0F);
						double d1 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
						double d2 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f = this.explosionSize
								* (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						double d4 = this.explosionX;
						double d6 = this.explosionY;
						double d8 = this.explosionZ;

						for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
							BlockPos blockpos = new BlockPos(d4, d6, d8);
							IBlockState iblockstate = this.worldObj
									.getBlockState(blockpos);

							if (iblockstate.getBlock().getMaterial() != Material.air) {
								float f2 = this.exploder != null ? this.exploder
										.getExplosionResistance(this,
												this.worldObj, blockpos,
												iblockstate) : iblockstate
										.getBlock().getExplosionResistance(
												worldObj, blockpos,
												(Entity) null, this);
								f -= (f2 + 0.3F) * 0.3F;
							}

							if (f > 0.0F
									&& (this.exploder == null || this.exploder
											.func_174816_a(this, this.worldObj,
													blockpos, iblockstate, f))) {
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
		j = MathHelper.floor_double(this.explosionX - (double) f3 - 1.0D);
		k = MathHelper.floor_double(this.explosionX + (double) f3 + 1.0D);
		int j1 = MathHelper.floor_double(this.explosionY - (double) f3 - 1.0D);
		int l = MathHelper.floor_double(this.explosionY + (double) f3 + 1.0D);
		int k1 = MathHelper.floor_double(this.explosionZ - (double) f3 - 1.0D);
		int i1 = MathHelper.floor_double(this.explosionZ + (double) f3 + 1.0D);
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this.exploder, new AxisAlignedBB((double) j, (double) j1,
						(double) k1, (double) k, (double) l, (double) i1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(
				this.worldObj, this, list, f3);
		Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);

		for (int l1 = 0; l1 < list.size(); ++l1) {
			Entity entity = (Entity) list.get(l1);

			if (entity.equals(exploder))
				continue;

			if (!entity.func_180427_aV()) {
				double d12 = entity.getDistance(this.explosionX,
						this.explosionY, this.explosionZ) / (double) f3;

				if (d12 <= 1.0D) {
					double d5 = entity.posX - this.explosionX;
					double d7 = entity.posY + (double) entity.getEyeHeight()
							- this.explosionY;
					double d9 = entity.posZ - this.explosionZ;
					double d13 = (double) MathHelper.sqrt_double(d5 * d5 + d7
							* d7 + d9 * d9);

					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						// Being inside of a block cannot protect from explosion
						double d14 = 1.0F;
						double d10 = (1.0D - d12) * d14;
						float amount = (float) ((int) ((d10 * d10 + d10) / 5.0D
								* 8.0D * (double) f3 + 1.0D));

						entity.attackEntityFrom(
								BioticExplosionDamage.explosionFrom(exploder),
								amount > limit ? limit : amount);
						double d11 = EnchantmentProtection.func_92092_a(entity,
								d10);
						entity.motionX += d5 * d11;
						entity.motionY += d7 * d11;
						entity.motionZ += d9 * d11;

						if (entity instanceof EntityPlayer) {
							this.field_77288_k.put((EntityPlayer) entity,
									new Vec3(d5 * d10, d7 * d10, d9 * d10));
						}
					}
				}
			}
		}
	}

	private float calculateDamage(Entity entity) {
		float f3 = this.explosionSize * 2.0F;

		float amount = 0.0F;

		double d12 = entity.getDistance(this.explosionX, this.explosionY,
				this.explosionZ) / (double) f3;

		if (d12 <= 1.0D) {
			double d5 = entity.posX - this.explosionX;
			double d7 = entity.posY + (double) entity.getEyeHeight()
					- this.explosionY;
			double d9 = entity.posZ - this.explosionZ;
			double d13 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9
					* d9);

			if (d13 != 0.0D) {
				d5 /= d13;
				d7 /= d13;
				d9 /= d13;
				// Being inside of a block cannot protect from explosion
				double d14 = 1.0F;
				double d10 = (1.0D - d12) * d14;
				amount = (float) ((int) ((d10 * d10 + d10) / 5.0D * 8.0D
						* (double) f3 + 1.0D));
			}
		}

		return amount;

	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */
	public void doExplosionB(boolean doParticles) {
		this.worldObj.playSoundEffect(this.explosionX, this.explosionY,
				this.explosionZ, Sounds.BIOTIC_EXPLOSION, 4.0F,
				(1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand
						.nextFloat()) * 0.2F) * 0.7F);

		if (doParticles) {
			if (this.explosionSize >= 2.0F && isSmoking)
				doParticles(500);
			else
				doParticles(300);

		}
	}

	/**
	 * Sets maximum damage for explosion.
	 * 
	 */
	public void setLimit(float amount) {
		limit = amount;
	}

	protected void doParticles(int amount) {

		final float particleRange = explosionSize * 0.20F;

		if (!worldObj.isRemote) {

			TargetPoint point = new TargetPoint(exploder.dimension, explosionX,
					explosionY, explosionZ, 75);

			double x = explosionX;
			double y = explosionY;
			double z = explosionZ;

			HeroesNetwork.client().spawnParticles(
					EnumParticleTypes.SPELL_INSTANT, x, y, z, particleRange,
					amount, EnumRandomType.GAUSSIAN, point);

		}

	}

}
