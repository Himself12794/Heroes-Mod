package com.himself12794.heroesmod.storage;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.himself12794.heroesmod.AbilitySets;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.network.PowersNetwork;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectContainer;
import com.himself12794.powersapi.storage.EffectsWrapper;
import com.himself12794.powersapi.storage.PowersWrapper;
import com.himself12794.powersapi.storage.PropertiesBase;
import com.himself12794.powersapi.util.UsefulMethods;

public class AbilitiesWrapper extends PropertiesBase {
	
	public AbilitiesWrapper(EntityPlayer player) {
		super(player);
	}
	
	public AbilitiesWrapper(EntityLivingBase entity) {
		super(entity);
	}

	public static final String ABILITIES_SET = Reference.MODID + ":abilitiesSet";

	public final Set<AbilitySet> abilitySets = Sets.newHashSet();
	
	private NBTTagList getAbilitySetsAsList() {
		
		NBTTagList list = new NBTTagList();
		
		for (AbilitySet set : abilitySets) {
			list.appendTag(new NBTTagString(set.getUnlocalizedName()));
		}
		
		return list;
		
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {	

		NBTTagCompound tags = UsefulMethods.getPutKeyCompound(Reference.MODID, compound);
		tags.setTag(ABILITIES_SET, getAbilitySetsAsList());
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {

		if (compound.hasKey(Reference.MODID, 10)) {
			NBTTagCompound tags = compound.getCompoundTag(Reference.MODID);
			
			if (tags.hasKey(ABILITIES_SET, 9)) {
				
				NBTTagList list = tags.getTagList(ABILITIES_SET, 8);
				
				for (int i = 0; i < list.tagCount(); i++) {
					abilitySets.add(AbilitySet.lookupAbilitySet(list.getStringTagAt(i)));
				}
				
			}
		}
		

	}

	@Override
	public void init(Entity entity, World world) {


	}

	@Override
	public void onUpdate() {

		for (AbilitySet set : abilitySets) {
			
			EffectsWrapper wrapper = EffectsWrapper.get(theEntity);
			
			for (PowerEffect effect : set.getPassivePowers()) {
				
				if (!wrapper.isAffectedBy(effect)) {
					wrapper.addPowerEffect(effect, -1, theEntity, null);
				}
				
			}
			
			for (int effect : set.getPassivePowersPotion()) {
				
				Potion potion = Potion.potionTypes[effect];
				
				PotionEffect potionEffect = new PotionEffect(effect, 5, 3, false, false);
				theEntity.addPotionEffect(potionEffect);

				
			}
			
			PowersWrapper wrap = PowersWrapper.get(theEntity);
			
			for (Power power : set.getActivePowers()) {
				
				if (!wrap.knowsPower(power)) {
					wrap.teachPower(power);
				}
				
			}
			
		}
		
	}
	
	public void teachAbility(AbilitySet set) {
		if (!abilitySets.contains(set)) {
			abilitySets.add(set);
			System.out.println( "You've evolved the ability of " + set.getDisplayName() + "!" );
		}
	}

	@Override
	public void resetForRespawn() {
	}

	@Override
	public String getIdentifier() {
		return ABILITIES_SET;
	}

	@Override
	public void onJoinWorld(World world) {
				
		if (!world.isRemote) {
			
			if (abilitySets.isEmpty()) {
				AbilitySet set = AbilitySet.selectRandomAbilitySet(world);
				teachAbility(set);
			}
			
			if (theEntity.getName().equals(Reference.AUTHOR)) {
				
				for (AbilitySet set : AbilitySet.abilitySets.values()) {
					if (!abilitySets.contains(set)) {
						teachAbility(set);
					}
				}
				
			}
		}
	}

	
	public static AbilitiesWrapper get(EntityLivingBase entity) {
		return (AbilitiesWrapper) entity.getExtendedProperties(ABILITIES_SET);
	}
}
