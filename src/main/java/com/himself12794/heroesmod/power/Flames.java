package com.himself12794.heroesmod.power;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.himself12794.heroesmod.ModConfig;
import com.himself12794.heroesmod.network.HeroesNetwork;
import com.himself12794.heroesmod.util.EnumRandomType;
import com.himself12794.heroesmod.util.FlamesType;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.entity.EntityPower;
import com.himself12794.powersapi.power.PowerRanged;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.util.UsefulMethods;

public class Flames extends PowerRanged {
	
	public Flames() {
		setMaxConcentrationTime(5 * 20);
		setMaxFunctionalState(3);
		setPower(6.0F);
		setCost(100);
		setDuration(8 * 20);
		setPreparationTime(20);
		setRange(1.0F);
		setUnlocalizedName("flames");
	}	
	
	public boolean canCastPower(PowerProfile profile) {
		boolean flag = !profile.theEntity.isInsideOfMaterial(Material.water);
		if (flag) profile.theEntity.playSound("fire.ignite", 1.5F, 1);
		return flag;
	}
	
	public boolean onCast(World world, EntityLivingBase caster, float modifier, int state) {
		caster.playSound(Reference.MODID + ":flamethrower", 0.5F, 1);
		return true;
	}
	
	public boolean onStrike(World world, MovingObjectPosition target, EntityLivingBase caster, float modifier, int state ) {
		
		if (caster.isInsideOfMaterial(Material.water)) return false;
		
		FlamesType burnState = FlamesType.getFlamesTypeByStateId(state);
		
		if (burnState.canBurnPosition(target, world) || isEntity(target)) {

			if (isEntity(target) && !target.entityHit.isImmuneToFire()) {
				
				if(burnState.burnsEntity()) target.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) caster).setFireDamage(), this.getPower(modifier));
				target.entityHit.setFire(burnState.burnsEntity() ? getDuration() / 20 : 1);
				
			} else if (burnState.burnsBlocks() && caster instanceof EntityPlayer){
				doBurn(target.getBlockPos(), target.sideHit, (EntityPlayer) caster, world, burnState);
			} 
			
		}
		
		return true;
		
	}
	
	@Override
	public void onStateChanged(World world, EntityLivingBase caster, int prevState, int currState) {
		
		if (world.isRemote && prevState != currState) caster.addChatMessage( new ChatComponentText( FlamesType.getFlamesTypeByStateId(currState).text ) );
		
	}
	
	@Override
	public int getMaxFunctionalState(PowerProfile profile) { 
		return ModConfig.getFlamethrowingLevel();
	}
	
	@Override
	public void onKnowledgeTick(PowerProfile profile) {
		
		if (profile.getState() > ModConfig.getFlamethrowingLevel()) {
			profile.setState(getMaxFunctionalState(profile), false);
		}
		
	}
	
	@Override
	public void onUpdate(EntityPower spell) {
		
		if (!spell.isInWater()) {
			
			World world = spell.worldObj;
			float distTraveled = getSpellVelocity() * spell.getTicksInAir();
			
			if (distTraveled >= 5) {
				
				spell.setDead();
				
			}
			
			awaylabel:
			
			for (float j = 0.0F; j < 1.0F; j += 0.05F) {
				
				for (int i = 0; i < 10; ++i) {
					
					BlockPos pos = new BlockPos(
							spell.prevPosX + (spell.motionX * j),
							spell.prevPosY + (spell.motionY * j),
							spell.prevPosZ + (spell.motionZ * j));
					
					if (UsefulMethods.getBlockAtPos(pos, world).getMaterial().isSolid()) {
						break awaylabel;
					}
						
					world.spawnParticle(spell.castState < 4 ? EnumParticleTypes.FLAME : EnumParticleTypes.LAVA,
						spell.prevPosX + (spell.motionX * j) - world.rand.nextFloat() * 0.5F,
						spell.prevPosY + (spell.motionY * j) - world.rand.nextFloat() * 0.5F,
						spell.prevPosZ + (spell.motionZ * j) - world.rand.nextFloat() * 0.5F,
						0, 0, 0);
					
				}
			}

			
			if (spell.getTicksInGround() > 0) spell.setDead();
			
		} else spell.setDead();
		
	}
	
	public void doBurn(BlockPos pos, EnumFacing side, EntityPlayer playerIn, World worldIn, FlamesType type) {
		
		if (UsefulMethods.getBlockAtPos(pos, worldIn) == Blocks.tnt) {
			BlockTNT tnt = (BlockTNT)Blocks.tnt;
			
			tnt.explode(worldIn, pos, tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)), playerIn);
			worldIn.setBlockToAir(pos);
			
		} else {
			
			if (!type.incinerates()) {
		
				pos = pos.offset(side);
		
		        if (playerIn.canPlayerEdit(pos, side, null)) {
		            if (worldIn.isAirBlock(pos)) {
		                worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
		            }
		        }
			} else {
				if (playerIn.canPlayerEdit(pos, side, null)) {
					worldIn.setBlockToAir(pos);
					/*for (int i = 0; i < 10; ++i) {
						worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, 
								pos.getX(), pos.getY(), pos.getZ(), 
								worldIn.rand.nextGaussian() * 0.5, 
								worldIn.rand.nextGaussian() * 0.5, 
								worldIn.rand.nextGaussian() * 0.5);
					}*/
					if (!worldIn.isRemote) {
						HeroesNetwork.client().spawnParticles(EnumParticleTypes.SMOKE_NORMAL, 
								pos.getX(), pos.getY(), pos.getZ(), 
								0.5F, 100, EnumRandomType.GAUSSIAN, null);
					}
				}
			}
		}
	}
	
	private static boolean isEntity(MovingObjectPosition target) {
		return target.typeOfHit == MovingObjectType.ENTITY;
	}
	
	@Override
	public String getDisplayName(PowerProfile profile) {
		return FlamesType.getFlamesTypeByStateId(profile.getState()).title;
	}
	
	public boolean isPiercingSpell() { return true; }
	
	public float getSpellVelocity() { return 5.0F; }

}
