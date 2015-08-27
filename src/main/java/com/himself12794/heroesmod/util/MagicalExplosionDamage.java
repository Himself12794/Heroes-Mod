package com.himself12794.heroesmod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;


public class MagicalExplosionDamage extends EntityDamageSource {

	public MagicalExplosionDamage(Entity damageSourceEntityIn) {
		super( "player.magicalExplosion", damageSourceEntityIn );
		setDamageBypassesArmor();
		setMagicDamage();
		setExplosion();
	}
	
	


    /**
     * Gets the death message that is displayed when the player dies
     */
    public IChatComponent getDeathMessage(EntityLivingBase p_151519_1_) {
    	
        String s = "death.attack." + this.damageType;
        String s1 = s + ".power";
        return new ChatComponentTranslation(s1, new Object[] {p_151519_1_.getDisplayName(), this.damageSourceEntity.getDisplayName()});
    }
    
    public static MagicalExplosionDamage explosionFrom(Entity exploder) {
    	return new MagicalExplosionDamage(exploder);
    }

}
