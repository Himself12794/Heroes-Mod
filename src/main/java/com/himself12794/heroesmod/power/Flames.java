package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.powersapi.config.Config;
import com.himself12794.powersapi.entity.EntitySpell;
import com.himself12794.powersapi.power.PowerRanged;
import com.himself12794.powersapi.util.UsefulMethods;
import com.himself12794.heroesmod.util.Reference;

public class Flames extends PowerRanged {
	
	public Flames() {
		setMaxConcentrationTime(5 * 20);
		setPower(3.0F);
		setCoolDown(100);
		setDuration(8 * 20);
		setUnlocalizedName("flames");
	}	
	
	public boolean onPreparePower(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		boolean flag = !playerIn.isInsideOfMaterial(Material.water);
		if (flag) playerIn.playSound("fire.ignite", 1.5F, 1);
		return flag;
	}
	
	public boolean onCast(World world, EntityLivingBase caster, ItemStack stack, float modifier) {
		caster.playSound(Reference.MODID + ":flamethrower", 0.5F, 1);
		return true;
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier ) {
		
		if (target.entityHit != null) {
			
			if (!target.entityHit.isImmuneToFire()) {
				
				target.entityHit.attackEntityFrom(DamageSource.inFire, this.getPower());
				target.entityHit.setFire(this.getDuration() / 20 );
				
			}
			
		} 
		
		if (Config.flamethrowing > 0 && target.getBlockPos() != null){
			
			BlockPos blockPos = UsefulMethods.getBlockFromSide( target.getBlockPos(), target.sideHit);
			Block block = UsefulMethods.getBlockAtPos(blockPos, world);
			
			if (Config.flamethrowing >= 2 && block.getMaterial().getCanBurn()) {
				
				world.setBlockState(blockPos, Blocks.fire.getDefaultState());
				
				if (Config.flamethrowing == 3 && world.isAirBlock(blockPos.up())) {
					
					world.setBlockState(blockPos.up(), Blocks.fire.getDefaultState());
					
				}
			
			} else if (Config.flamethrowing == 1 && block.getMaterial() == Material.vine) {
				
				world.setBlockState(blockPos, Blocks.fire.getDefaultState());
			
			} 
		}
		
		return true;
		
	}
	
	public void onUpdate(EntitySpell spell) {
		
		if (!spell.isInWater()) {
			
			World world = spell.worldObj;
			float distTraveled = getSpellVelocity() * spell.getTicksInAir();
			
			if (distTraveled >= 5) {
				
				spell.setDead();
				
			}
			
			final boolean cont = true;
			
			if (cont) {
				
				for (float j = 0.0F; j < 1.0F; j += 0.05F) {
					
					for (int i = 0; i < 10; ++i) {
						
						BlockPos pos = new BlockPos(
								spell.prevPosX + (spell.motionX * j),
								spell.prevPosY + (spell.motionY * j),
								spell.prevPosZ + (spell.motionZ * j));
						
						if (UsefulMethods.getBlockAtPos(pos, world).getMaterial().isSolid()) break;
							
						world.spawnParticle(EnumParticleTypes.FLAME,
							spell.prevPosX + (spell.motionX * j) - world.rand.nextFloat() * 0.5F,
							spell.prevPosY + (spell.motionY * j) - world.rand.nextFloat() * 0.5F,
							spell.prevPosZ + (spell.motionZ * j) - world.rand.nextFloat() * 0.5F,
							0, 0, 0);
						
					}
				}
			}
			
			if (spell.getTicksInGround() > 0) spell.setDead();
			
		} else spell.setDead();
		
	}
	
	public boolean isPiercingSpell() { return true; }
	
	public float getSpellVelocity() { return 5.0F; }

}
