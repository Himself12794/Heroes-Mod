package com.himself12794.heroesmod.world;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.BioticExplosionDamage;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.Reference.Sounds;

/**
 * Damages like a regular explosion, but its particles and sound are different,
 * it doesn't damage the caster, shockwave passes through blocks, but does not destroy them.
 * Damage can also be limited.
 * 
 * @author Himself12794
 *
 */
public class BioticExplosion extends Explosion {

	@SuppressWarnings("unused")
	private final Random explosionRNG;
	private final World worldObj;
	private final double explosionX;
	private final double explosionY;
	private final double explosionZ;
	private final Entity exploder;
	private final float explosionSize;
	@SuppressWarnings("unused")
	private final Vec3 position;
	private float limit = 50.0F;

	public BioticExplosion(World worldIn, Entity exploder, double expX,
			double expY, double expZ, float size,
			boolean isSmoking) {

		super(worldIn, exploder, expX, expY, expZ, size, false, true);

		this.explosionRNG = new Random();
		this.worldObj = worldIn;
		this.exploder = exploder;
		this.explosionSize = size;
		this.explosionX = expX;
		this.explosionY = expY;
		this.explosionZ = expZ;
		this.position = new Vec3(explosionX, explosionY, explosionZ);
	}

	/**
	 * Does the first part of the explosion. Explosion cannot destroy blocks, but still damages through them.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void doExplosionA() {
		
		float f3 = this.explosionSize * 2.0F;
		int j = MathHelper.floor_double(this.explosionX - (double) f3 - 1.0D);
		int k = MathHelper.floor_double(this.explosionX + (double) f3 + 1.0D);
		int j1 = MathHelper.floor_double(this.explosionY - (double) f3 - 1.0D);
		int l = MathHelper.floor_double(this.explosionY + (double) f3 + 1.0D);
		int k1 = MathHelper.floor_double(this.explosionZ - (double) f3 - 1.0D);
		int i1 = MathHelper.floor_double(this.explosionZ + (double) f3 + 1.0D);
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity( this.exploder, new AxisAlignedBB((double) j, (double) j1, (double) k1, (double) k, (double) l, (double) i1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(worldObj, this, list, f3);
		Vec3 vec3 = new Vec3(explosionX, explosionY, explosionZ);

		for (int l1 = 0; l1 < list.size(); ++l1) {
			
			Entity entity = (Entity) list.get(l1);

			if (entity.equals(exploder)) continue;
			
			// I assume this function is whether or not the entity is immune to explosions
			if (!entity.func_180427_aV()) {
				double d12 = entity.getDistance(explosionX, explosionY, explosionZ) / (double) f3;

				if (d12 <= 1.0D) {
					double d5 = entity.posX - explosionX;
					double d7 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
					double d9 = entity.posZ - explosionZ;
					double d13 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);

					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = 1.0F;
						double d10 = (1.0D - d12) * d14;
						float damage = (float) ((int) ((d10 * d10 + d10) / 5.0D * 8.0D * (double) f3 + 1.0D));
						
						
						entity.attackEntityFrom(BioticExplosionDamage.explosionFrom(exploder), damage > limit ? limit : damage);
						
						// Protection Enchantment still helps mitigate knockback
						double d11 = EnchantmentProtection.func_92092_a(entity, d10);
						entity.motionX += d5 * d11;
						entity.motionY += d7 * d11;
						entity.motionZ += d9 * d11;
					}
				}
			}
		}
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
			if (explosionSize >= 2.0F)
				doParticles(500);
			else
				doParticles(300);

		}
	}

	/**
	 * Sets maximum damage for explosion. Must be called before
	 * {@code Explosion.doExplosionA()} to be of any use.
	 * 
	 */
	public void setDamageLimit(float amount) {
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
	
	public static void doExplosion(World world, Entity caster, double x, double y, double z, float size, float maxDamage) {
		BioticExplosion splodey = new BioticExplosion(world, caster, x, y, z, size, true);
		splodey.setDamageLimit(maxDamage);
		splodey.doExplosionA();
		splodey.doExplosionB(true);
	}
	
	public static void doExplosion(World world, Entity caster, float size, float maxDamage) {
		BioticExplosion splodey = new BioticExplosion(world, caster, caster.posX, caster.posY, caster.posZ, size, true);
		splodey.setDamageLimit(maxDamage);
		splodey.doExplosionA();
		splodey.doExplosionB(true);
	}

}
