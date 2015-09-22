package com.himself12794.heroesmod.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.himself12794.heroesmod.ModConfig;
import com.himself12794.powersapi.util.UsefulMethods;

public enum FlamesType {
	
	WAR_FLAMES(0, "War Flames", "Setting fire to entities", true, false, false),
	BRUSH_FLAMES(1, "Brush Flames", "Incinerating grass", false, true, true, Material.vine),
	HEDGING_FLAMES(2, "Hedging Flames", "Incinerating grass and leaves", false, true, true, Material.leaves, Material.vine),
	DEMOLITION_FLAMES(3, "Demolition Flames", "Setting fire to burnable objects", false, true, false, getBurnableMaterials()),
	NAPALM_FLAMES(4, "Napalm Flames", "Setting fire to everything", true, true, false );
	
	public final int permissionLevel;
	public final int stateId;
	public final String title;
	public final String text;
	private final boolean burnsEntities;
	private final boolean burnsBlocks;
	/** Whether or not the target should be set on fire, or instantly removed */
	private final boolean incinerates;
	private final List burnMaterials;	
	
	FlamesType(int stateId, int permissionLevel, String title, String text, boolean burnsEntities, boolean burnsBlocks, boolean incinerates, List materials) {
		this.permissionLevel = permissionLevel;
		this.stateId = stateId;
		this.title = title;
		this.text = text;
		this.burnsEntities = burnsEntities;
		this.burnsBlocks = burnsBlocks;
		this.incinerates = incinerates;
		this.burnMaterials = materials;
	}
	
	FlamesType(int permissionLevel, String title, String text, boolean burnsEntities, boolean burnsBlocks, boolean incinerates, List materials) {
		this(permissionLevel, permissionLevel, title, text, burnsEntities, burnsBlocks, incinerates, materials);
	}
	
	FlamesType(int id, String title, String text, boolean burnsEntities, boolean burnsBlocks, boolean incinerates, Material...materials) {
		this(id, title, text, burnsEntities, burnsBlocks, incinerates, Lists.newArrayList(materials));
	}
	
	public boolean canBurnPosition(MovingObjectPosition pos, World world) {
		
		if (permissionLevel <= ModConfig.getFlamethrowingLevel()) {
			
			if (pos.typeOfHit == MovingObjectType.ENTITY) {
				return burnsEntities;
			} else if (pos.typeOfHit == MovingObjectType.BLOCK) {
				return burnsBlocks && burnsMaterial(UsefulMethods.getBlockAtPos(pos.getBlockPos(), world).getMaterial());
			}
			
		} 
		return false;
	}
	
	public boolean burnsMaterial(Material mat) {
		return burnMaterials.contains(mat) || (burnsBlocks && burnMaterials.size() == 0);
	}
	
	public boolean burnsEntity() {
		return burnsEntities;
	}
	
	public boolean burnsBlocks() {
		return burnsBlocks;
	}
	
	public boolean incinerates() {
		return incinerates;
	}
	
	public static FlamesType getFlamesTypeByStateId(int id) {
		
		for (FlamesType type : values()) {
			if (type.stateId == id) return type;
		}
		
		return FlamesType.WAR_FLAMES;
	}
	
	private static List getBurnableMaterials() {
		List burnables = Lists.newArrayList();
		
		for (Field field : Material.class.getFields()) {
			int mods = field.getModifiers();
			if (Modifier.isFinal(mods) && Modifier.isPublic(mods) && Modifier.isStatic(mods) && field.getType() == Material.class) {
				try {
					Material mat = (Material)field.get(null);
					if (mat.getCanBurn()) {
						burnables.add(mat);
					}
				} catch (Exception e) {
					// Don't care what happens here.
				}
			}
		}
		
		return burnables;
	}
	
}