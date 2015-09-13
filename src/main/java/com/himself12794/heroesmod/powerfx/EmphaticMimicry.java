package com.himself12794.heroesmod.powerfx;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import com.google.common.base.Predicate;
import com.himself12794.heroesmod.AbilitySets;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.storage.AbilitiesWrapper;
import com.himself12794.powersapi.power.EffectType;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;

public class EmphaticMimicry extends PowerEffect {
	
	private final float mimicryRange = 50.0F;
	
	public EmphaticMimicry() {
		super("emphaticMimicry");
		this.setNegateable();
		this.setPersistant();
		this.setType(EffectType.BENEFICIAL);
	}
	
	public boolean onUpdate(final EntityLivingBase entity, final int timeLeft, final EntityLivingBase caster, final Power initiatedPower){ 
		
		List<EntityPlayer> players = entity.worldObj.getPlayers(EntityPlayer.class, new Predicate<EntityPlayer>() {

			@Override
			public boolean apply(EntityPlayer input) {
				return input.getDistanceToEntity(entity) <= mimicryRange 
						&& !AbilitiesWrapper.get(input).abilitySets.isEmpty() 
						&& !AbilitiesWrapper.get(input).abilitySets.contains(AbilitySets.emphaticMimicry);
			}
			
		}); 
		
		AbilitiesWrapper wrapper = AbilitiesWrapper.get(entity);
		
		for (EntityPlayer player : players) {
			
			for (AbilitySet set : AbilitiesWrapper.get(player).abilitySets) {
				
				if (!wrapper.abilitySets.contains(set)) {
					wrapper.abilitySets.add(set);
					entity.addChatMessage( new ChatComponentText( "You just learned " + set.getDisplayName() + " from " + player.getName() + "!") );
				}
				
			}
			
		}
		
		return true; 
	}

}
