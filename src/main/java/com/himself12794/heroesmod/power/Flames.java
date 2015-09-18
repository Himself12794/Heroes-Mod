package com.himself12794.heroesmod.power;

import net.minecraft.block.Block;
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
		setCooldown(100);
		setDuration(8 * 20);
		setPreparationTime(20);
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
		
		BurnState burnState = BurnState.getBurnStateById(state);
		
		if (burnState.canBurnPosition(target, world) || isEntity(target)) {

			if (isEntity(target) && !target.entityHit.isImmuneToFire()) {
				
				if(burnState.burnsEntity()) target.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) caster).setFireDamage(), this.getPower(modifier));
				target.entityHit.setFire(burnState.burnsEntity() ? getDuration() : 1);
				
			} else if (burnState.burnsBlocks() && caster instanceof EntityPlayer){
				setFireToPos(target.getBlockPos(), target.sideHit, (EntityPlayer) caster, world);
			} 
			
		}
		
		return true;
		
	}
	
	@Override
	public void onStateChanged(World world, EntityLivingBase caster, int prevState, int currState) {
		
		if (world.isRemote && prevState != currState) caster.addChatMessage( new ChatComponentText( BurnState.getBurnStateById(currState).text ) );
		
	}
	
	@Override
	public int getMaxFunctionalState(PowerProfile profile) { 
		return ModConfig.flamethrowing;
	}
	
	@Override
	public void onKnowledgeTick(PowerProfile profile) {
		
		if (profile.getState() > ModConfig.flamethrowing) {
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
					
					if (UsefulMethods.getBlockAtPos(pos, world).getMaterial().isSolid()) break awaylabel;
						
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
	
	public void setFireToPos(BlockPos pos, EnumFacing side, EntityPlayer playerIn, World worldIn) {
		
		if (UsefulMethods.getBlockAtPos(pos, worldIn) == Blocks.tnt) {
			BlockTNT tnt = (BlockTNT)Blocks.tnt;
			
			tnt.explode(worldIn, pos, tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)), playerIn);
			worldIn.setBlockToAir(pos);
			
			return;
		}
		
		pos = pos.offset(side);

        if (playerIn.canPlayerEdit(pos, side, null)) {
            if (worldIn.isAirBlock(pos)) {
                worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
            }
        }
	}
	
	private static boolean isEntity(MovingObjectPosition target) {
		return target.typeOfHit == MovingObjectType.ENTITY;
	}
	
	@Override
	public String getDisplayName(PowerProfile profile) {
		return BurnState.getBurnStateById(profile.getState()).title;
	}
	
	public boolean isPiercingSpell() { return true; }
	
	public float getSpellVelocity() { return 5.0F; }
	
	public static enum BurnState {
		
		LEVEL_ZERO_BURN(0, "War Flames", "Setting fire to entities"),
		LEVEL_ONE_BURN(1, "Brush Flames", "Setting fire to grass"),
		LEVEL_TWO_BURN(2, "Hedging Flames", "Setting fire to grass and leaves"),
		LEVEL_THREE_BURN(3, "Demolition Flames", "Setting fire to burnable objects"),
		LEVEL_FOUR_BURN(4, "Napalm Flames", "Setting fire to everything");
		
		public final int id;
		public final String title;
		public final String text;
		
		BurnState(int id, String title, String text) {
			this.id = id;
			this.title = title;
			this.text = text;
		}
		
		public boolean canBurnPosition(MovingObjectPosition pos, World world) {
			
			BlockPos blockPos = pos.typeOfHit == MovingObjectType.ENTITY ? pos.entityHit.getPosition() : pos.getBlockPos();
			Block block = UsefulMethods.getBlockAtPos(blockPos, world);
			
			switch(this) {
				case LEVEL_ZERO_BURN:
					return pos.typeOfHit == MovingObjectType.ENTITY;
				case LEVEL_ONE_BURN:
					return ModConfig.flamethrowing >= 1 && block.getMaterial() == Material.vine;
				case LEVEL_TWO_BURN:
					return ModConfig.flamethrowing >= 2 && (block.getMaterial() == Material.leaves || block.getMaterial() == Material.vine);
				case LEVEL_THREE_BURN:
					return ModConfig.flamethrowing >= 3 && block.getMaterial().getCanBurn();
				case LEVEL_FOUR_BURN:
					return ModConfig.flamethrowing == 4;
				default:
					return false;
			}
		}
		
		public boolean burnsEntity() {
			return this.id == 0 || this.id == 4;
		}
		
		public boolean burnsBlocks() {
			return this.id != 0;
		}
		
		public static BurnState getBurnStateById(int state) {
			if (state > BurnState.values().length - 1 || state < 0) {
				return null;
			} else {
				return BurnState.values()[state];
			}
		}
		
	}

}
