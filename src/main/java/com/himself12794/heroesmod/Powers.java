package com.himself12794.heroesmod;

import com.himself12794.heroesmod.power.Dummy;
import com.himself12794.heroesmod.power.DummyHoming;
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
	
	public static final Power damage;
	public static final Incinerate incinerate;
	public static final Lightning lightning;
	public static final Heal heal;
	public static final Power death;
	public static final Dummy dummy;
	public static final Immortalize immortalize;
	public static final Flames flames;
	public static final DummyHoming dummyHoming;
	public static final Slam slam;
	public static final Push push;
	public static final Telekinesis telekinesis;
	public static final Phasing phasing;
	
	static {

		damage = Power.lookupPower("damage");	
		death = Power.lookupPower("power");	
		incinerate = (Incinerate) Power.lookupPower("incinerate");
		lightning = (Lightning) Power.lookupPower("lightning");
		heal = (Heal) Power.lookupPower("heal");
		dummy = (Dummy) Power.lookupPower("dummy");
		immortalize = (Immortalize) Power.lookupPower("immortalize");
		flames = (Flames) Power.lookupPower("flames");
		dummyHoming = (DummyHoming) Power.lookupPower("dummyHoming");
		slam = (Slam) Power.lookupPower("slam");
		push = (Push) Power.lookupPower("push");
		telekinesis = (Telekinesis) Power.lookupPower("telekinesis");
		phasing = (Phasing) Power.lookupPower("phasing");
		
	}
}
