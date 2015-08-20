package com.himself12794.heroesmod;

import com.himself12794.heroesmod.power.Dummy;
import com.himself12794.heroesmod.power.DummyHoming;
import com.himself12794.heroesmod.power.ExplodingFireball;
import com.himself12794.heroesmod.power.Flames;
import com.himself12794.heroesmod.power.Heal;
import com.himself12794.heroesmod.power.Immortalize;
import com.himself12794.heroesmod.power.Incinerate;
import com.himself12794.heroesmod.power.Lightning;
import com.himself12794.heroesmod.power.Phasing;
import com.himself12794.heroesmod.power.Push;
import com.himself12794.heroesmod.power.Slam;
import com.himself12794.heroesmod.power.Telekinesis;
import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerInstant;

public class Powers {

	public static final Power DAMAGE;
	public static final Incinerate INCINERATE;
	public static final Lightning LIGHTNING;
	public static final Heal HEAL;
	public static final Power DEATH;
	public static final Dummy DUMMY;
	public static final Immortalize IMMORTALIZE;
	public static final Flames FLAMES;
	public static final DummyHoming DUMMY_HOMING;
	public static final Slam SLAM;
	public static final Push PUSH;
	public static final Telekinesis TELEKINESIS;
	public static final Phasing PHASING;
	public static final ExplodingFireball EXPLODING_FIREBALL;

	static {

		if (HeroesMod.instance.isInitialized()) {

			DAMAGE = Power.lookupPower("damage");
			DEATH = Power.lookupPower("power");
			INCINERATE = (Incinerate) Power.lookupPower("incinerate");
			LIGHTNING = (Lightning) Power.lookupPower("lightning");
			HEAL = (Heal) Power.lookupPower("heal");
			DUMMY = (Dummy) Power.lookupPower("dummy");
			IMMORTALIZE = (Immortalize) Power.lookupPower("immortalize");
			FLAMES = (Flames) Power.lookupPower("flames");
			DUMMY_HOMING = (DummyHoming) Power.lookupPower("dummyHoming");
			SLAM = (Slam) Power.lookupPower("slam");
			PUSH = (Push) Power.lookupPower("push");
			TELEKINESIS = (Telekinesis) Power.lookupPower("telekinesis");
			PHASING = (Phasing) Power.lookupPower("phasing");
			EXPLODING_FIREBALL = Power.lookupPower(ExplodingFireball.class);

		} else {
			throw new RuntimeException(
					"References accessed before initialization");
		}

	}
}
