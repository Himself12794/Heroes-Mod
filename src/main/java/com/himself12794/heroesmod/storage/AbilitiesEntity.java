package com.himself12794.heroesmod.storage;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.himself12794.heroesmod.AbilitySets;
import com.himself12794.heroesmod.ability.AbilitySet;
import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerEffect;
import com.himself12794.powersapi.storage.EffectsEntity;
import com.himself12794.powersapi.storage.PowersEntity;
import com.himself12794.powersapi.storage.PropertiesBase;
import com.himself12794.powersapi.util.UsefulMethods;

public class AbilitiesEntity extends PropertiesBase {

	private static final String ABILITIES_SET = Reference.MODID + ":abilitiesSet";
	private static final String HAS_JOINED_BEFORE = "abilitiesSet";

	public final Set<AbilitySet> abilitySets = Sets.newHashSet();
	
	public boolean hasJoinedWorldBefore;
	
	public AbilitiesEntity(EntityPlayer player) {
		super(player);
	}
	
	public AbilitiesEntity(EntityLivingBase entity) {
		super(entity);
	}
	
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
		tags.setBoolean(HAS_JOINED_BEFORE, hasJoinedWorldBefore);
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {

		if (compound.hasKey(Reference.MODID, 10)) {
			NBTTagCompound tags = compound.getCompoundTag(Reference.MODID);
			hasJoinedWorldBefore = tags.getBoolean(HAS_JOINED_BEFORE);
			
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
			
			EffectsEntity wrapper = EffectsEntity.get(theEntity);
			
			for (PowerEffect effect : set.getPassivePowers()) {
				
				if (effect == null) continue;
				
				if (!wrapper.isAffectedBy(effect)) {
					wrapper.addPowerEffect(effect, -1, theEntity, null);
				}
				
			}
			
			for (int effect : set.getPassivePowersPotion()) {
				
				Potion potion = Potion.potionTypes[effect];
				
				PotionEffect potionEffect = new PotionEffect(effect, 5, 3, false, false);
				theEntity.addPotionEffect(potionEffect);

				
			}
			
			PowersEntity wrap = PowersEntity.get(theEntity);
			
			for (Power power : set.getActivePowers()) {
				
				if (!wrap.knowsPower(power)) {
					wrap.teachPower(power);
				}
				
			}
			
		}
		
		if (abilitySets.contains(null))
			abilitySets.remove(null);
		
	}
	
	public void teachAbility(AbilitySet set) {
		if (!abilitySets.contains(set)) {
			abilitySets.add(set);
			if (theEntity.worldObj.isRemote)
				theEntity.addChatMessage( new ChatComponentText( "You've evolved the ability of " + set.getDisplayName() + "!" ) );
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
			
			if (abilitySets.isEmpty() && !hasJoinedWorldBefore) {
				AbilitySet set = AbilitySet.selectRandomAbilitySet(world);
				teachAbility(set);
				hasJoinedWorldBefore = true;
			}
			
			if (theEntity.getName().equals(Reference.MOD_AUTHOR)) {
				
				//for (AbilitySet set : AbilitySet.abilitySets.values()) {
				//	if (!abilitySets.contains(set)) {
				//		teachAbility(set);
				//	}
				//}
				if (!abilitySets.contains(AbilitySets.emphaticMimicry))
					teachAbility(AbilitySets.emphaticMimicry);
				
			}
		}
	}

	
	public static AbilitiesEntity get(EntityLivingBase entity) {
		return (AbilitiesEntity) entity.getExtendedProperties(ABILITIES_SET);
	}
}
