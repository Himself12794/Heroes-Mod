package com.himself12794.heroesmod;

import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerInstant;
import com.himself12794.heroesmod.power.*;

public class Powers {
	
	public static Power damage;
	public static Incinerate incinerate;
	public static Lightning lightning;
	public static Heal heal;
	public static Power death;
	public static Dummy dummy;
	public static Immortalize immortalize;
	public static Flames flames;
	public static DummyHoming dummyHoming;
	public static Slam slam;
	public static Push push;
	public static Telekinesis telekinesis;
	
	public static void registerPowers(){

		damage = Power.registerPower(new PowerInstant().setUnlocalizedName("damage"));	
		death = Power.registerPower(new PowerInstant().setUnlocalizedName("death").setPower(1000.0F).setCoolDown(178));	
		incinerate = (Incinerate) Power.registerPower(new Incinerate());
		lightning = (Lightning) Power.registerPower(new Lightning());
		heal = (Heal) Power.registerPower(new Heal());
		dummy = (Dummy) Power.registerPower(new Dummy());
		immortalize = (Immortalize) Power.registerPower(new Immortalize());
		flames = (Flames) Power.registerPower(new Flames());
		dummyHoming = (DummyHoming) Power.registerPower(new DummyHoming());
		slam = (Slam) Power.registerPower(new Slam());
		push = (Push) Power.registerPower(new Push());
		telekinesis = (Telekinesis) Power.registerPower(new Telekinesis());
		
	}
}
