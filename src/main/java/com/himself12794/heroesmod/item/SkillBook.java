package com.himself12794.heroesmod.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.himself12794.heroesmod.util.Reference;
import com.himself12794.powersapi.storage.PowerProfile;
import com.himself12794.powersapi.storage.PowersEntity;

public class SkillBook extends Item {
	
	private final String name = "skillBook";
	
	public SkillBook() {
		setMaxStackSize(1);
		setHasSubtypes(true);
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(Reference.MODID + "_" + name);
		setCreativeTab(CreativeTabs.tabTools);
	}
	    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
    	
    	PowersEntity powers = PowersEntity.get(player);
    	
    	PowerProfile profile = null;
    	
    	if(forPrimary(stack) && powers.getPrimaryPower() != null) {
    		profile = powers.getPowerProfile(powers.getPrimaryPower());
    	} else if (forSecondary(stack) && powers.getSecondaryPower() != null) {
    		profile = powers.getPowerProfile(powers.getSecondaryPower());
    	}
    	
    	if (profile != null) {
    		if (profile.thePower.getMaxLevel(profile) > profile.level) {
    			profile.levelUp();
    			stack.stackSize--;
    		}
    	}
    	
    	return stack;
    }
   
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        
    	NBTTagCompound primary = new NBTTagCompound();
    	primary.setString("ForPower", "primary");
    	
    	NBTTagCompound secondary = new NBTTagCompound();
    	secondary.setString("ForPower", "secondary");
    	
    	ItemStack p = new ItemStack(itemIn);
    	p.setTagCompound(primary);
    	
    	ItemStack s = new ItemStack(itemIn);
    	s.setTagCompound(secondary);
    	
        subItems.add(p);
        subItems.add(s);  		
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
    	String name;
    	
    	if (forPrimary(stack)) {
    		name = getUnlocalizedName() + ".primary";
    	} else if (forSecondary(stack)) {
    		name = getUnlocalizedName() + ".secondary";
    	} else {
    		name = getUnlocalizedName();
    	}
    	
        return name;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
    	return forPrimary(stack) || forSecondary(stack);
    }
    
    private boolean forPrimary(ItemStack stack) {
    	
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null) {
    		return false;
    	} else {
    		return nbt.getString("ForPower").equals("primary");
    	}
    	
    }

    private boolean forSecondary(ItemStack stack) {
    	
    	NBTTagCompound nbt = stack.getTagCompound();
    	if (nbt == null) {
    		return false;
    	} else {
    		return nbt.getString("ForPower").equals("secondary");
    	}
    	
    }
    
    public String getName() {
    	return name;
    }
}
