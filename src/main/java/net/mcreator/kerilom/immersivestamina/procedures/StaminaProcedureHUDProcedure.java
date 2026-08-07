package net.mcreator.kerilom.immersivestamina.procedures;

import net.minecraft.world.entity.Entity;

import net.mcreator.kerilom.immersivestamina.network.ImmersiveStaminaModVariables;
import net.mcreator.kerilom.immersivestamina.configuration.ConfigConfiguration;

public class StaminaProcedureHUDProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		double index = 0;
		double inpercentsstamina = 0;
		inpercentsstamina = GetPercentFromValueProcedure.execute(entity.getCapability(ImmersiveStaminaModVariables.PLAYER_VARIABLES).orElseGet(ImmersiveStaminaModVariables.PlayerVariables::new).stamina,
				(double) ConfigConfiguration.MAX_STAMINA.get());
		if (inpercentsstamina >= 100) {
			index = 20;
		} else if (inpercentsstamina >= 95) {
			index = 19;
		} else if (inpercentsstamina >= 90) {
			index = 18;
		} else if (inpercentsstamina >= 85) {
			index = 17;
		} else if (inpercentsstamina >= 80) {
			index = 16;
		} else if (inpercentsstamina >= 75) {
			index = 15;
		} else if (inpercentsstamina >= 70) {
			index = 14;
		} else if (inpercentsstamina >= 65) {
			index = 13;
		} else if (inpercentsstamina >= 60) {
			index = 12;
		} else if (inpercentsstamina >= 55) {
			index = 11;
		} else if (inpercentsstamina >= 50) {
			index = 10;
		} else if (inpercentsstamina >= 45) {
			index = 9;
		} else if (inpercentsstamina >= 40) {
			index = 8;
		} else if (inpercentsstamina >= 35) {
			index = 7;
		} else if (inpercentsstamina >= 30) {
			index = 6;
		} else if (inpercentsstamina >= 25) {
			index = 5;
		} else if (inpercentsstamina >= 20) {
			index = 4;
		} else if (inpercentsstamina >= 15) {
			index = 3;
		} else if (inpercentsstamina >= 10) {
			index = 2;
		} else if (inpercentsstamina >= 5) {
			index = 1;
		} else if (inpercentsstamina >= 0) {
			index = 0;
		}
		return index;
	}
}