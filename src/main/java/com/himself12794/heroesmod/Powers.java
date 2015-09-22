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

	public static final Telekinesis telekinesis;
	public static final Incinerate incinerate;
	public static final Lightning lightning;
	public static final Heal heal;
	public static final Dummy dummy;
	public static final Flames flames;
	public static final Slam slam;
	public static final Punt punt;
	public static final Flare explodingBolt;
	public static final Eclipse eclipse;
	public static final BlockMemory blockMemory;
	public static final Charge charge;
	public static final Nova nova;
	public static final Launch launch; 
	public static final SpecializedPunch specializedPunch;
	public static final Power speedBoost;
	public static final Power enderAccess;

	static {

		if (HeroesMod.instance().isInitialized()) {

			incinerate = PowersRegistry.lookupPower(Incinerate.class);
			lightning = PowersRegistry.lookupPower(Lightning.class);
			heal = PowersRegistry.lookupPower(Heal.class);
			dummy = PowersRegistry.lookupPower(Dummy.class);
			flames = PowersRegistry.lookupPower(Flames.class);
			slam = PowersRegistry.lookupPower(Slam.class);
			punt = PowersRegistry.lookupPower(Punt.class);
			telekinesis = PowersRegistry.lookupPower(Telekinesis.class);
			explodingBolt = PowersRegistry.lookupPower(Flare.class);
			eclipse = PowersRegistry.lookupPower(Eclipse.class);
			blockMemory = PowersRegistry.lookupPower(BlockMemory.class);
			charge = PowersRegistry.lookupPower(Charge.class);
			nova = PowersRegistry.lookupPower(Nova.class);
			launch = PowersRegistry.lookupPower(Launch.class);
			specializedPunch = PowersRegistry.lookupPower(SpecializedPunch.class);
			speedBoost = PowersRegistry.lookupPower("speedBoost");
			enderAccess = PowersRegistry.lookupPower("enderAccess");

		} else {
			throw new RuntimeException("References accessed before initialization");
		}

	}
}
