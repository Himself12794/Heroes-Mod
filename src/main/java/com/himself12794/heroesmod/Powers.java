package com.himself12794.heroesmod;

import com.himself12794.heroesmod.power.BlockMemory;
import com.himself12794.heroesmod.power.Charge;
import com.himself12794.heroesmod.power.Dummy;
import com.himself12794.heroesmod.power.Eclipse;
import com.himself12794.heroesmod.power.Flames;
import com.himself12794.heroesmod.power.Flare;
import com.himself12794.heroesmod.power.Heal;
import com.himself12794.heroesmod.power.Incinerate;
import com.himself12794.heroesmod.power.Lightning;
import com.himself12794.heroesmod.power.Nova;
import com.himself12794.heroesmod.power.Punt;
import com.himself12794.heroesmod.power.Slam;
import com.himself12794.heroesmod.power.Telekinesis;
import com.himself12794.powersapi.power.Power;

public class Powers {

	public static final Power DAMAGE;
	public static final Power IMMORTALIZE;
	public static final Power DEATH;
	public static final Power BREAK;
	public static final Power PHASING;
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
	public static final BlockMemory BLOCK_REMEMBER;
	public static final Charge CHARGE;
	public static final Nova NOVA;

	static {

		if (HeroesMod.instance().isInitialized()) {

			DAMAGE = Power.lookupPower("damage");
			DEATH = Power.lookupPower("power");
			INCINERATE = Power.lookupPower(Incinerate.class);
			LIGHTNING = Power.lookupPower(Lightning.class);
			HEAL = Power.lookupPower(Heal.class);
			DUMMY = Power.lookupPower(Dummy.class);
			IMMORTALIZE = Power.lookupPower("immortalize");
			FLAMES = Power.lookupPower(Flames.class);
			SLAM = Power.lookupPower(Slam.class);
			PUNT = Power.lookupPower(Punt.class);
			TELEKINESIS = Power.lookupPower(Telekinesis.class);
			PHASING = Power.lookupPower("phasing");
			EXPLODING_BOLT = Power.lookupPower(Flare.class);
			ECLIPSE = Power.lookupPower(Eclipse.class);
			BREAK = Power.lookupPower("break");
			BLOCK_REMEMBER = Power.lookupPower(BlockMemory.class);
			CHARGE = Power.lookupPower(Charge.class);
			NOVA = Power.lookupPower(Nova.class);

		} else {
			throw new RuntimeException(
					"References accessed before initialization");
		}

	}
}
