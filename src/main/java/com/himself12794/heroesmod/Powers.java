package com.himself12794.heroesmod;

import com.himself12794.heroesmod.power.BlockMemory;
import com.himself12794.heroesmod.power.Charge;
import com.himself12794.heroesmod.power.Dummy;
import com.himself12794.heroesmod.power.Eclipse;
import com.himself12794.heroesmod.power.Flames;
import com.himself12794.heroesmod.power.Flare;
import com.himself12794.heroesmod.power.Heal;
import com.himself12794.heroesmod.power.Incinerate;
import com.himself12794.heroesmod.power.Launch;
import com.himself12794.heroesmod.power.Lightning;
import com.himself12794.heroesmod.power.Nova;
import com.himself12794.heroesmod.power.Punt;
import com.himself12794.heroesmod.power.Slam;
import com.himself12794.heroesmod.power.SpecializedPunch;
import com.himself12794.heroesmod.power.Telekinesis;
import com.himself12794.powersapi.PowersRegistry;
import com.himself12794.powersapi.power.Power;

public class Powers {

	public static final Telekinesis TELEKINESIS;
	public static final Incinerate INCINERATE;
	public static final Lightning LIGHTNING;
	public static final Heal HEAL;
	public static final Dummy DUMMY;
	public static final Flames FLAMES;
	public static final Slam SLAM;
	public static final Punt PUNT;
	public static final Flare EXPLODING_BOLT;
	public static final Eclipse ECLIPSE;
	public static final BlockMemory BLOCK_MEMORY;
	public static final Charge CHARGE;
	public static final Nova NOVA;
	public static final Launch LAUNCH; 
	public static final SpecializedPunch SPECIALIZED_PUNCH;
	public static final Power SPEED_BOOST;
	public static final Power ENDER_ACCESS;

	static {

		if (HeroesMod.instance().isInitialized()) {

			INCINERATE = PowersRegistry.lookupPower(Incinerate.class);
			LIGHTNING = PowersRegistry.lookupPower(Lightning.class);
			HEAL = PowersRegistry.lookupPower(Heal.class);
			DUMMY = PowersRegistry.lookupPower(Dummy.class);
			FLAMES = PowersRegistry.lookupPower(Flames.class);
			SLAM = PowersRegistry.lookupPower(Slam.class);
			PUNT = PowersRegistry.lookupPower(Punt.class);
			TELEKINESIS = PowersRegistry.lookupPower(Telekinesis.class);
			EXPLODING_BOLT = PowersRegistry.lookupPower(Flare.class);
			ECLIPSE = PowersRegistry.lookupPower(Eclipse.class);
			BLOCK_MEMORY = PowersRegistry.lookupPower(BlockMemory.class);
			CHARGE = PowersRegistry.lookupPower(Charge.class);
			NOVA = PowersRegistry.lookupPower(Nova.class);
			LAUNCH = PowersRegistry.lookupPower(Launch.class);
			SPECIALIZED_PUNCH = PowersRegistry.lookupPower(SpecializedPunch.class);
			SPEED_BOOST = PowersRegistry.lookupPower("power.speedBoost");
			ENDER_ACCESS = PowersRegistry.lookupPower("power.enderAccess");

		} else {
			throw new RuntimeException("References accessed before initialization");
		}

	}
}
