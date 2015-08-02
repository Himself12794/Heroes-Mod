package com.himself12794.heroesmod.power;

import com.himself12794.powersapi.power.Power;
import com.himself12794.powersapi.power.PowerInstant;

public class Powers {
	
	public static void registerPowers() {

		Power.registerPower(new PowerInstant().setUnlocalizedName("damage"));	
		Power.registerPower(new PowerInstant().setUnlocalizedName("death").setPower(1000.0F).setCoolDown(178));	
		Power.registerPower(new Incinerate());
		Power.registerPower(new Lightning());
		Power.registerPower(new Heal());
		Power.registerPower(new Dummy());
		Power.registerPower(new Immortalize());
		Power.registerPower(new Flames());
		Power.registerPower(new DummyHoming());
		Power.registerPower(new Slam());
		Power.registerPower(new Push());
		Power.registerPower(new Telekinesis());
		Power.registerPower(new Phasing());
		
	}
}
