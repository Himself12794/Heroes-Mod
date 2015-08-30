package com.himself12794.heroesmod.power;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.himself12794.heroesmod.PowerEffects;
import com.himself12794.heroesmod.util.DWrapper;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.power.PowerEffectActivatorBuff;
import com.himself12794.powersapi.power.PowerEffectActivatorInstant;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.powersapi.util.UsefulMethods;

public class BlockRecall extends PowerInstant {

	public BlockRecall() {
		setUnlocalizedName("blockRecall");
		setRange(5);
	}

	public boolean onStrike(World world, MovingObjectPosition target,
			EntityLivingBase caster, float modifier) {

		DWrapper wrap = DWrapper.get(caster);
		//wrap.setSavedBlockPos1(BlockPos.ORIGIN);

		if (!wrap.getSavedBlockPos1().equals(BlockPos.ORIGIN)) {

			if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

				BlockPos placePos = UsefulMethods.getBlockFromSideSwap(target.getBlockPos(), target.sideHit);

				if (world.isAirBlock(placePos)) {
					
					if (world.isRemote) caster.addChatMessage( new ChatComponentText("Moved " + wrap.getSavedBlockPos1().toString() + " to " + placePos.toString()));
					
					IBlockState original = world.getBlockState(wrap
							.getSavedBlockPos1());

					world.setBlockState(placePos, original);
					world.setBlockToAir(wrap.getSavedBlockPos1());

					wrap.setSavedBlockPos1(BlockPos.ORIGIN);

					return true;
				}

			}

		} else if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			BlockPos pos = target.getBlockPos();
			float hardness = world.getBlockState(pos).getBlock().getBlockHardness(world, pos);
			if (hardness >= 0.0F && hardness <= 50.0F) {
				
				if (world.isRemote) caster.addChatMessage(new ChatComponentText("remebering block at " + pos));
				
				DWrapper.get(caster).setSavedBlockPos1(pos);
				return true;
			}

		}

		return false;

	}

}
